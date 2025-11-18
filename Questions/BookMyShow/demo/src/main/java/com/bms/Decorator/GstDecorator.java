package com.bms.Decorator;

public class GstDecorator extends PriceDecorator {
    private final double gstRate = 18;
    public GstDecorator(double amount){
        super(amount);
    }

    @Override
    public double getAmount(){
        return amount * (1.0 + gstRate/100.0);
    }
}
