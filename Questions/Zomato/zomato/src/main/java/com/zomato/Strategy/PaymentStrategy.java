package com.zomato.Strategy;

import com.zomato.Models.Order;
import com.zomato.Models.Payment;

public interface PaymentStrategy {
    Payment pay(Order order, double amount);
}