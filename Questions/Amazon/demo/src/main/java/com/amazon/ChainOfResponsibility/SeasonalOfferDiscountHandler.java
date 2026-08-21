package com.amazon.ChainOfResponsibility;

public class SeasonalOfferDiscountHandler extends DiscountHandler {
    @Override
    public double apply(double amount) {
        double discount = amount * 0.05;
        System.out.println("Applying seasonal discount: " + discount);
        if (next != null) {
            return discount + next.apply(amount - discount);
        }
        return discount;
    }
}
