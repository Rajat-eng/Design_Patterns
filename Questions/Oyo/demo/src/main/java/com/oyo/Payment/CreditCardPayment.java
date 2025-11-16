package com.oyo.Payment;

public class CreditCardPayment implements Payment {
    @Override
    public boolean processPayment(double amount) {
        // Process credit card payment
        return true;
    }
}
