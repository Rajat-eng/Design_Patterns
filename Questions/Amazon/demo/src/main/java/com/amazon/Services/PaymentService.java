package com.amazon.Services;

import com.amazon.Interface.PaymentStrategy;
public class PaymentService {
    public boolean processPayment(PaymentStrategy strategy, double amount) {
        return strategy.pay(amount);
    }
}
