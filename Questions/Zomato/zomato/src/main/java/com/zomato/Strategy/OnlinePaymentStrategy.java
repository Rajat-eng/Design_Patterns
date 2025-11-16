package com.zomato.Strategy;

import com.zomato.Enums.PaymentStatus;
import com.zomato.Models.Order;
import com.zomato.Models.Payment;

public class OnlinePaymentStrategy implements PaymentStrategy {
    @Override
    public Payment pay(Order order, double amount) {
        Payment payment = new Payment(amount);
        // Simulate online payment processing
        payment.setStatus(PaymentStatus.SUCCESS);
        return payment;
    }
    
}
