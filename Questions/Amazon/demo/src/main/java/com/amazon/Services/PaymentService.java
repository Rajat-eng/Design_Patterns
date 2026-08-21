package com.amazon.Services;

import com.amazon.Strategy.PaymentStrategy;

public class PaymentService {
    public boolean processPayment(PaymentStrategy strategy, double amount) {
        try {
            return strategy.pay(amount);
        } catch (Exception e) {
            System.err.println("Payment processing failed: " + e.getMessage());
            return false;
        }
    }

    public boolean processRefund(PaymentStrategy strategy, double amount) {
        try {
            return strategy.refund(amount);
        } catch (Exception e) {
            System.err.println("Refund processing failed: " + e.getMessage());
            return false;
        }
    }
}
