package com.bms.Services;

import com.bms.Enums.PaymentStatus;
import com.bms.Models.Payment;
import com.bms.Strategy.payment.PaymentStrategy;

public class PaymentService {
    private final PaymentStrategy paymentStrategy;

    public PaymentService(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public Payment charge(double amount) {
        return paymentStrategy.pay(amount);
    }

    public boolean refund(Payment payment) {
        if (payment == null) {
            return false;
        }

        System.out.println("Refunding payment " + payment.getTransactionId() + " with amount " + payment.getAmount());
        return true;
    }
}
