package Patterns.Abstract_Factory;

import Patterns.Abstract_Factory.Class.Client;
import Patterns.Abstract_Factory.Class.RazorpayFactory;
import Patterns.Abstract_Factory.Class.StripeFactory;
import Patterns.Abstract_Factory.Interface.PaymentGatewayFactory;

public class Main {
    public static void main(String[] args) {
        PaymentGatewayFactory stripeFactory = new StripeFactory();
        Client stripeClient = new Client(stripeFactory);
        stripeClient.processPayment(5000, "TXN123");

        PaymentGatewayFactory razorpayFactory = new RazorpayFactory();
        Client razorpayClient = new Client(razorpayFactory);
        razorpayClient.processPayment(2500, "TXN456");
    }
}
