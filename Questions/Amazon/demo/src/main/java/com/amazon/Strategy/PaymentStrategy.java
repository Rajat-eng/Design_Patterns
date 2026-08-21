package com.amazon.Strategy;

public interface PaymentStrategy {
    boolean pay(double amount);

    boolean refund(double amount);
}