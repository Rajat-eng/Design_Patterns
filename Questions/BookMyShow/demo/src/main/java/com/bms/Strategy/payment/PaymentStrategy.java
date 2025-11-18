package com.bms.Strategy.payment;


import com.bms.Models.Payment;

public interface PaymentStrategy {
    Payment pay(double amount);
}
