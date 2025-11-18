package com.bms.Decorator;

public abstract class PriceDecorator implements Price {
    public double amount;

    public PriceDecorator(double amount){
        this.amount = amount;
    }
}
