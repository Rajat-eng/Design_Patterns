package com.zomato.Models;

import com.zomato.Enums.PaymentStatus;

public class Payment {
    private String paymentId;
    private double amount;
    private PaymentStatus status;

    public Payment(double amount) {
        this.paymentId = java.util.UUID.randomUUID().toString();
        this.amount = amount;
        this.status = PaymentStatus.INITIATED;
    }

    public String getPaymentId() { return paymentId; }
    public double getAmount() { return amount; }
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
}

