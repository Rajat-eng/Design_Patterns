package com.amazon.ChainOfResponsibility;

public abstract class DiscountHandler {
    protected DiscountHandler next;

    public void setNext(DiscountHandler next) {
        this.next = next;
    }

    public abstract double apply(double amount);
}
