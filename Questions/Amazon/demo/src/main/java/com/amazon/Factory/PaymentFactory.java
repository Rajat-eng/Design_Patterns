package com.amazon.Factory;

import com.amazon.Strategy.CreditCardPaymentStrategy;
import com.amazon.Strategy.PaymentStrategy;
import com.amazon.Strategy.UPIPaymentStrategy;

public class PaymentFactory {
    public PaymentStrategy createPaymentStrategy(String type, String paymentDetail) {
        switch (type.toLowerCase()) {
            case "creditcard":
                return new CreditCardPaymentStrategy(paymentDetail);
            case "upi":
                return new UPIPaymentStrategy(paymentDetail);
            default:
                throw new IllegalArgumentException("Unsupported payment type: " + type);
        }
    }
}
