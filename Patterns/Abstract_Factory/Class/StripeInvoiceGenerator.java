package Patterns.Abstract_Factory.Class;

import Patterns.Abstract_Factory.Interface.InvoiceGenerator;

public class StripeInvoiceGenerator implements InvoiceGenerator {
    @Override
    public void generateInvoice(String transactionId) {
        System.out.println("Generating Stripe invoice for transaction: " + transactionId);
    }
}
