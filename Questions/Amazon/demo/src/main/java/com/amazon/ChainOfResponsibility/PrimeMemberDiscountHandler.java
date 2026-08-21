package com.amazon.ChainOfResponsibility;

public class PrimeMemberDiscountHandler extends DiscountHandler {
    @Override
    public double apply(double amount) {
        double discount = amount * 0.10;
        System.out.println("Applying Prime Member discount: " + discount);
        if (next != null) {
            return discount + next.apply(amount - discount);
        }
        return discount;
    }
}
