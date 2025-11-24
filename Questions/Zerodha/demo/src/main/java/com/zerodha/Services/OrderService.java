package com.zerodha.Services;

import com.zerodha.Enums.ExchangeType;
import com.zerodha.Enums.OrderStatus;
import com.zerodha.Enums.OrderType;
import com.zerodha.Factory.ExchangeRouterFactory;
import com.zerodha.Models.Order.Order;
import com.zerodha.Models.Order.SellOrder;
import com.zerodha.Services.Exchange.ExchangeRouter;
import com.zerodha.State.FailedState;


public class OrderService {

    private final ExchangeRouterFactory exchangeFactory;
    private final MarketDataService marketData;

    public OrderService(ExchangeRouterFactory exchangeFactory,
                        MarketDataService marketData) {
        this.exchangeFactory = exchangeFactory;
        this.marketData = marketData;
    }

    public boolean placeOrder(Order order, ExchangeType exchangeType) {
        System.out.println("[OrderService] Received order " + order.getOrderId());

        if (!reserve(order)) {
            fail(order, "Reservation failed");
            return false;
        }

        ExchangeRouter router = exchangeFactory.getRouter(exchangeType);
        router.submit(order);
        return true;
    }

    public void cancelOrder(Order order, ExchangeType exchangeType) {
        ExchangeRouter router = exchangeFactory.getRouter(exchangeType);
        router.cancel(order);
    }

    private boolean reserve(Order order) {
        if (order.isBuy()) {
            double price = estimatePrice(order);
            double required = price * order.getOriginalQty();

            boolean ok = order.getUser().account.reserve(required);
            if (!ok) {
                fail(order, "Insufficient funds");
                return false;
            }

            System.out.printf("[Reserve] Reserved %.2f for BUY %s%n",
                    required, order.getOrderId());
            return true;
        }

        if (order instanceof SellOrder sell) {
            try {
                sell.reserveHoldingsForSell();
                System.out.printf("[Reserve] Reserved %d shares for SELL %s%n",
                        order.getOriginalQty(), order.getOrderId());
                return true;
            } catch (RuntimeException ex) {
                fail(order, "Insufficient holdings");
                return false;
            }
        }

        return true;
    }

    private double estimatePrice(Order order) {
        if (order.getType() == OrderType.LIMIT) {
            return order.getLimitPrice();
        }
        double ltp = marketData.getLTP(order.getInstrument());
        return (ltp > 0) ? ltp : 100.0; // fallback
    }

    private void fail(Order order, String reason) {
        System.out.printf("[OrderService] %s for %s%n", reason, order.getOrderId());
        order.setState(new FailedState());
        order.setStatus(OrderStatus.FAILED);
        order.onStatusChange();
    }
}
