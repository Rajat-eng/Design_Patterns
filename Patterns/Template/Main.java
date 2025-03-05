package Patterns.Template;

import Patterns.Template.Class.CreditCardPayment;
import Patterns.Template.Class.OnlinePayment;
import Patterns.Template.Class.PayPalPayment;

public class Main {
    public static void main(String[] args) {
        System.out.println("---- Credit Card Payment ----");
        OnlinePayment creditCard = new CreditCardPayment();
        creditCard.processPayment();

        System.out.println("\n---- PayPal Payment ----");
        OnlinePayment payPal = new PayPalPayment();
        payPal.processPayment();
    }
}
