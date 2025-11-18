package com.bms.Decorator;

public class BasePrice implements Price {
    private final double amount;

    public BasePrice(double amount) {
        this.amount = amount;
    }

    @Override
    public double getAmount() {
        return amount;
    }
}
