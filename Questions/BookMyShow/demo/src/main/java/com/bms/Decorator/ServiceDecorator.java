package com.bms.Decorator;

public class ServiceDecorator extends PriceDecorator {
    private final double serviceRate = 15;
    public ServiceDecorator(Price price){
        super(price);
    }

    @Override
    public double getAmount(){
        return price.getAmount() * (1.0 + serviceRate/100.0);
    }
}
