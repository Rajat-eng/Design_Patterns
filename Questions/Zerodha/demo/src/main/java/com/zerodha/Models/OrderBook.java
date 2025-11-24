package com.zerodha.Models;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;

import com.zerodha.Enums.OrderType;
import com.zerodha.Models.Order.Order;

/**
 * Pure OrderBook implementation.
 * No business logic — only data structure for bids & asks.
 *
 * Uses:
 *  bids = TreeMap DESC (best = highest)
 *  asks = TreeMap ASC  (best = lowest)
 */
public class OrderBook {

    private final NavigableMap<Double, Deque<Order>> bids =
            new TreeMap<>(Comparator.reverseOrder());
            // highest price which buyer offers

    private final NavigableMap<Double, Deque<Order>> asks =
            new TreeMap<>();
            // lowest price a seller will accept

    // -------------------------
    // Add Order
    // -------------------------
    public void add(Order order) {
        NavigableMap<Double, Deque<Order>> book = order.isBuy() ? bids : asks;

        // For market orders, internally use price 0, but they should rarely stay in the book.
        double price = (order.getType() == OrderType.MARKET)
                ? 0.0
                : order.getLimitPrice(); // max buy price 

        Deque<Order> dq = book.computeIfAbsent(price, p -> new ArrayDeque<>());
        dq.addLast(order);
    }

    // -------------------------
    // Remove Order
    // -------------------------
    public void remove(Order order) {
        NavigableMap<Double, Deque<Order>> book = order.isBuy() ? bids : asks;

        double price = (order.getType() == OrderType.MARKET)
                ? 0.0
                : order.getLimitPrice();

        Deque<Order> dq = book.get(price);
        if (dq != null) {
            dq.remove(order);
            if (dq.isEmpty()) book.remove(price);
        }
    }

    // -------------------------
    // Best Bid / Best Ask
    // -------------------------
    public Optional<Double> bestBidPrice() {
        return bids.isEmpty() ? Optional.empty() : Optional.of(bids.firstKey());
    }

    public Optional<Double> bestAskPrice() {
        return asks.isEmpty() ? Optional.empty() : Optional.of(asks.firstKey());
    }

    // -------------------------
    // Peek Best Opposite
    // -------------------------
    public Order bestBidOrder() {
        for (Deque<Order> dq : bids.values())
            if (!dq.isEmpty()) return dq.peekFirst();
        return null;
    }

    public Order bestAskOrder() {
        for (Deque<Order> dq : asks.values())
            if (!dq.isEmpty()) return dq.peekFirst();
        return null;
    }

    public Order peekOpposite(Order incoming) {
        return incoming.isBuy() ? bestAskOrder() : bestBidOrder();
    }

    public double getOppositePrice(Order incoming) {
        return incoming.isBuy()
                ? bestAskPrice().orElse(0.0)
                : bestBidPrice().orElse(0.0);
    }

    // -------------------------
    // Data inspection
    // -------------------------
    public Map<Double, List<Order>> snapshotBids() {
        Map<Double, List<Order>> copy = new LinkedHashMap<>();
        for (var e : bids.entrySet()) {
            copy.put(e.getKey(), new ArrayList<>(e.getValue()));
        }
        return copy;
    }

    public Map<Double, List<Order>> snapshotAsks() {
        Map<Double, List<Order>> copy = new LinkedHashMap<>();
        for (var e : asks.entrySet()) {
            copy.put(e.getKey(), new ArrayList<>(e.getValue()));
        }
        return copy;
    }

    public void printBook(String instrument) {
        System.out.printf("[OrderBook-%s] Asks:\n", instrument);
        asks.forEach((p, dq) -> {
            System.out.printf("  %.2f -> %s\n", p, dqDesc(dq));
        });
        System.out.printf("[OrderBook-%s] Bids:\n", instrument);
        bids.forEach((p, dq) -> {
            System.out.printf("  %.2f -> %s\n", p, dqDesc(dq));
        });
    }

    private String dqDesc(Deque<Order> dq) {
        StringBuilder sb = new StringBuilder();
        for (Order o : dq) {
            sb.append(String.format("[%s rem=%d] ",
                    o.getOrderId().substring(0, 6),
                    o.getRemainingQty()));
        }
        return sb.toString();
    }
}
