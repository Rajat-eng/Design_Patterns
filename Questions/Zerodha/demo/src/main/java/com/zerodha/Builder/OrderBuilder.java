package com.zerodha.Builder;

import com.zerodha.Enums.OrderType;
import com.zerodha.Enums.TransactionType;
import com.zerodha.Factory.OrderFactory;
import com.zerodha.Models.Order.Order;
import com.zerodha.Models.User;

public class OrderBuilder {
    private User user;
    private String instrument;
    private TransactionType tx;
    private OrderType type;
    private int qty;
    private double price;

    public OrderBuilder forUser(User u) { this.user = u; return this; }
    public OrderBuilder withInstrument(String s) { this.instrument = s; return this; }
    public OrderBuilder buy(int q) { this.tx = TransactionType.BUY; this.qty = q; return this; }
    public OrderBuilder sell(int q) { this.tx = TransactionType.SELL; this.qty = q; return this; }
    public OrderBuilder market() { this.type = OrderType.MARKET; return this; }
    public OrderBuilder limit(double p) { this.type = OrderType.LIMIT; this.price = p; return this; }

    public Order build() {
        return OrderFactory.create(user, instrument, tx, type, qty, price);
    }
}
