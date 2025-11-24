package com.zerodha.Models;



public class Trade {
    public final String id;
    public final String instrument;
    public final double price;
    public final int qty;
    public final String buyOrderId;
    public final String sellOrderId;

    public Trade(String id, String instrument, double price, int qty,
                 String buyOrderId, String sellOrderId) {
        this.id = id;
        this.instrument = instrument;
        this.price = price;
        this.qty = qty;
        this.buyOrderId = buyOrderId;
        this.sellOrderId = sellOrderId;
    }
}

