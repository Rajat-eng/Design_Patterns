package Patterns.Strategy;

import Patterns.Strategy.Class.CreditCardPayment;
import Patterns.Strategy.Class.GooglePayPayment;
import Patterns.Strategy.Class.PayPalPayment;
import Patterns.Strategy.Class.PaymentContext;

public class Main {
    public static void main(String[] args) {
        PaymentContext paymentContext = new PaymentContext();

        // Using Credit Card
        paymentContext.setPaymentStrategy(new CreditCardPayment("1234-5678-9876-5432"));
        paymentContext.processPayment(100.00);

        // Using PayPal
        paymentContext.setPaymentStrategy(new PayPalPayment("user@example.com"));
        paymentContext.processPayment(200.00);

        // Using Google Pay
        paymentContext.setPaymentStrategy(new GooglePayPayment("987-654-3210"));
        paymentContext.processPayment(300.00);
    }
}
