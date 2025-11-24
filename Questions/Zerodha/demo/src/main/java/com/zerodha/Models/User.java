package com.zerodha.Models;

import java.util.UUID;

import com.zerodha.Models.Order.Order;
import com.zerodha.Observer.StockObserver;

public class User implements StockObserver {
     public final String id;
    public final String name;
    public final Account account;
    public final Portfolio portfolio;

    public User(String id, String name, double initialBalance) {
        this.id = id;
        this.name = name;
        this.account = new Account(initialBalance);
        this.portfolio = new Portfolio();
    }

    public void orderStatusUpdate(Order order) {
        System.out.printf("[UserNotify] %s: order %s status=%s remaining=%d%n",
                name, order.getOrderId(), order.getStatus(), order.getRemainingQty());
    }

    @Override
    public String toString() {
        return String.format("User{id=%s,name=%s,balance=%.2f,portfolio=%s}", id, name, account.getBalance(), portfolio);
    }

    @Override
    public void update(Stock stock) {
        System.out.printf("[Notification for %s] Stock %s price updated to: $%.2f%n",
                name, stock.getSymbol(), stock.getPrice());
    }
}

