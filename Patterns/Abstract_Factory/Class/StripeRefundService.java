package Patterns.Abstract_Factory.Class;

import Patterns.Abstract_Factory.Interface.RefundService;

public class StripeRefundService implements RefundService {
    @Override
    public void refund(String transactionId) {
        System.out.println("Refunding via Stripe for transaction: " + transactionId);
    }
}
