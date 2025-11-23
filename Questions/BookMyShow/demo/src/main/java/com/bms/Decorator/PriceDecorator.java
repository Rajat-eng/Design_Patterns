package com.bms.Decorator;

public abstract class PriceDecorator implements Price {
    public Price price;

    public PriceDecorator(Price price){
        this.price = price;
    }
    
    @Override
    public abstract double getAmount();
}
