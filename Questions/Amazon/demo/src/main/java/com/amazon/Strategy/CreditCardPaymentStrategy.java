package com.amazon.Strategy;

public class CreditCardPaymentStrategy implements PaymentStrategy {
    private final String cardNumber;

    public CreditCardPaymentStrategy(String cardNumber) { this.cardNumber = cardNumber; }

    @Override
    public boolean pay(double amount) {
        System.out.printf("Processing credit card payment of $%.2f with card %s.%n", amount, cardNumber);
        // Simulate payment gateway logic
        return true;
    }

    @Override
    public boolean refund(double amount) {
        System.out.printf("Processing credit card refund of $%.2f to card %s.%n", amount, cardNumber);
        // Simulate refund gateway logic
        return true;
    }
}