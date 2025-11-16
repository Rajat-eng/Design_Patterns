package com.oyo.Payment;

public class Cashpayment implements Payment {
 @Override
    public boolean processPayment(double amount) {
        // Process cash payment
        return true;
    }
}
