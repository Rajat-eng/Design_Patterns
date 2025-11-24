package com.zerodha.Services;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.zerodha.Enums.OrderStatus;
import com.zerodha.Enums.OrderType;
import com.zerodha.Models.Order.Order;
import com.zerodha.Models.OrderBook;
import com.zerodha.Models.Trade;
import com.zerodha.State.FailedState;
import com.zerodha.State.PartiallyFilledState;

public class SymbolMatchingEngine {

    private final String instrument;
    private final OrderBook orderBook = new OrderBook();

    private final BlockingQueue<Order> queue = new LinkedBlockingQueue<>();

    // Thread pool of 3 workers
    private final ExecutorService workers = Executors.newFixedThreadPool(3);

    private final AtomicBoolean running = new AtomicBoolean(true);

    private final TradeService tradeService;

    public SymbolMatchingEngine(String instrument, TradeService tradeService) {
        this.instrument = instrument;
        this.tradeService = tradeService;

        startWorkers();
    }

    // -------------------------------------------------------------
    // Start worker threads
    // -------------------------------------------------------------
    private void startWorkers() {
        for (int i = 0; i < 3; i++) {
            workers.submit(() -> workerLoop());
        }
    }

    private void workerLoop() {
        while (running.get()) {
            try {
                Order incoming = queue.take();
                process(incoming);   // → synchronized matching
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    // -------------------------------------------------------------
    // Submit an order to the engine
    // -------------------------------------------------------------
    public void submit(Order order) {
        queue.offer(order);
    }

    // -------------------------------------------------------------
    // Main matching logic (synchronized = single-threaded matching)
    // -------------------------------------------------------------
    private synchronized void process(Order incoming) {

        System.out.printf("[Engine-%s] Processing %s%n", instrument, incoming.shortDesc());

        incoming.getStrategy().execute(incoming, this);

        // if market order still remains then we fail the order
        if (incoming.getRemainingQty() > 0) {
            if (incoming.getType() == OrderType.LIMIT) {
                orderBook.add(incoming);
                System.out.printf("[Engine-%s] Added to book %s%n",
                        instrument, incoming.shortDesc());
            } else {
                incoming.setStatus(OrderStatus.PARTIALLY_FILLED);
                incoming.setState(new PartiallyFilledState());
                incoming.onStatusChange();
                System.out.printf("[Engine-%s] Market order is partially filled (no liquidity)%n", instrument);
            }
        } else {
            System.out.printf("[Engine-%s] Order FULLY filled%n", instrument);
        }

        orderBook.printBook(instrument);
    }

    // -------------------------------------------------------------
    // Strategy interface methods
    // -------------------------------------------------------------
    public Order peekOpposite(Order incoming) {
        return orderBook.peekOpposite(incoming);
    }

    public double getOppositePrice(Order incoming) {
        return orderBook.getOppositePrice(incoming);
    }

    public void removeOrder(Order order) {
        orderBook.remove(order);
    }

    public void executeTrade(Order incoming, Order opposite, int qty, double price) {

        Order buy = incoming.isBuy() ? incoming : opposite;
        Order sell = incoming.isSell() ? incoming : opposite;

        buy.getUser().portfolio.add(instrument, qty);
        sell.getUser().account.deposit(price * qty);

        buy.applyFill(qty);
        sell.applyFill(qty);

        Trade t = new Trade(
                UUID.randomUUID().toString(),
                instrument,
                price,
                qty,
                buy.getOrderId(),
                sell.getOrderId()
        );

        tradeService.record(t);

        System.out.printf("[Engine-%s] TRADE %d @ %.2f | BUY=%s SELL=%s%n",
                instrument, qty, price,
                buy.getOrderId().substring(0, 6),
                sell.getOrderId().substring(0, 6));

        if (opposite.getRemainingQty() == 0) {
            removeOrder(opposite);
        }
    }

    // -------------------------------------------------------------
    // Shutdown engine
    // -------------------------------------------------------------
    public void shutdown() {
        running.set(false);
        workers.shutdownNow();
    }
}
