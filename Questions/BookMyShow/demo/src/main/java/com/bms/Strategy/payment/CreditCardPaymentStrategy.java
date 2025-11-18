package com.bms.Strategy.payment;

import java.util.UUID;

import com.bms.Enums.PaymentStatus;
import com.bms.Models.Payment;

public class CreditCardPaymentStrategy implements PaymentStrategy {
    private final String cardNumber;
    private final String cvv;

    public CreditCardPaymentStrategy(String cardNumber, String cvv) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    @Override
    public Payment pay(double amount) {
        System.out.printf("Processing credit card payment of $%.2f%n", amount);
        // Simulate payment gateway interaction
        boolean paymentSuccess = Math.random() > 0.05; // 95% success rate
        return new Payment(
                amount,
                paymentSuccess ? PaymentStatus.SUCCESS : PaymentStatus.FAILURE,
                "TXN_" + UUID.randomUUID()
        );
    }
}
