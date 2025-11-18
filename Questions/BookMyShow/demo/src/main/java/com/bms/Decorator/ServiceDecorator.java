package com.bms.Decorator;

public class ServiceDecorator extends PriceDecorator {
    private final double serviceRate = 15;
    public ServiceDecorator(double amount){
        super(amount);
    }

    @Override
    public double getAmount(){
        return amount * (1.0 + serviceRate/100.0);
    }
}
