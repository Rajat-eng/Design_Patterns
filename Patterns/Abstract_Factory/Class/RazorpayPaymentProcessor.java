package Patterns.Abstract_Factory.Class;

import Patterns.Abstract_Factory.Interface.PaymentProcessor;

public class RazorpayPaymentProcessor implements PaymentProcessor {
    @Override
    public void pay(double amount) {
        System.out.println("Processing payment via Razorpay: " + amount);
    }
}
