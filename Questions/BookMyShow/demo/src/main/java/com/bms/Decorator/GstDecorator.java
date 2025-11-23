package com.bms.Decorator;

public class GstDecorator extends PriceDecorator {
    private final double gstRate = 18;
    public GstDecorator(Price price){
        super(price);
    }

    @Override
    public double getAmount(){
        return  price.getAmount()* (1.0 + gstRate/100.0);
    }
}
