package com.zomato.Factory;

import com.zomato.Enums.PaymentType;
import com.zomato.Strategy.CashPaymentStrategy;
import com.zomato.Strategy.OnlinePaymentStrategy;
import com.zomato.Strategy.PaymentStrategy;

public class PaymentFactory {
    public static PaymentStrategy getPaymentStrategy(PaymentType type) {
        switch (type) {
            case CASH:
                return new CashPaymentStrategy();
            case ONLINE:
                return new OnlinePaymentStrategy();
            default:
                throw new IllegalArgumentException("Unknown payment type: " + type);
        }
    }
}
