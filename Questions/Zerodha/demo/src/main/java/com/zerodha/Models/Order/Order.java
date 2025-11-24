package com.zerodha.Models.Order;

import com.zerodha.Enums.*;
import com.zerodha.State.*;
import com.zerodha.Models.User;
import com.zerodha.Strategy.ExecutionStrategy;

public abstract class Order {
    protected final String orderId;
    protected final User user;
    protected final String instrument;
    protected final Side side;
    protected final OrderType type;
    protected final int originalQty;
    protected volatile int remainingQty;
    protected final double limitPrice;
    protected volatile OrderStatus status;
    protected volatile OrderState state;
    protected final ExecutionStrategy strategy;

    public Order(String id, User user, String instrument, Side side, OrderType type, int qty, double price, ExecutionStrategy strategy) {
        this.orderId = id;
        this.user = user;
        this.instrument = instrument;
        this.side = side;
        this.type = type;
        this.originalQty = qty;
        this.remainingQty = qty;
        this.limitPrice = price;
        this.strategy = strategy;
        this.state = new OpenState();
        this.state.onEnter(this);
    }

    public String getOrderId() { return orderId; }
    public User getUser() { return user; }
    public String getInstrument() { return instrument; }
    public Side getSide() { return side; }
    public OrderType getType() { return type; }
    public int getOriginalQty() { return originalQty; }
    public synchronized int getRemainingQty() { return remainingQty; }
    public double getLimitPrice() { return limitPrice; }
    public OrderStatus getStatus() { return status; }
    public OrderState getState() { return state; }
    public ExecutionStrategy getStrategy() { return strategy; }

    public boolean isBuy() { return side == Side.BUY; }
    public boolean isSell() { return side == Side.SELL; }

    public synchronized void applyFill(int qty) {
        if (qty <= 0) return;
        if (qty > remainingQty) qty = remainingQty;
        this.remainingQty -= qty;
        if (this.remainingQty == 0) {
            this.state = new FilledState();
            this.state.onEnter(this);
        } else {
            this.state = new PartiallyFilledState();
            this.state.onEnter(this);
        }
    }

    public void cancel() { state.cancel(this); }

    public void setState(OrderState s) { this.state = s; s.onEnter(this); }
    public void setStatus(OrderStatus s) { this.status = s; }

    public void onStatusChange() { user.orderStatusUpdate(this); }

    public abstract String shortDesc();
}
