package Patterns.Abstract_Factory.Class;

import Patterns.Abstract_Factory.Interface.RefundService;

public class RazorpayRefundService implements RefundService {
    @Override
    public void refund(String transactionId) {
        System.out.println("Refunding via Razorpay for transaction: " + transactionId);
    }
}
