package com.amazon.Strategy;

import com.amazon.Interface.PaymentStrategy;

public class CreditCardPaymentStrategy implements PaymentStrategy {
    private final String cardNumber;

    public CreditCardPaymentStrategy(String cardNumber) { this.cardNumber = cardNumber; }

    @Override
    public boolean pay(double amount) {
        System.out.printf("Processing credit card payment of $%.2f with card %s.%n", amount, cardNumber);
        // Simulate payment gateway logic
        return true;
    }
}