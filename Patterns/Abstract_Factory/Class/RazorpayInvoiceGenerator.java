package Patterns.Abstract_Factory.Class;

import Patterns.Abstract_Factory.Interface.InvoiceGenerator;

public class RazorpayInvoiceGenerator implements InvoiceGenerator {
    @Override
    public void generateInvoice(String transactionId) {
        System.out.println("Generating Razorpay invoice for transaction: " + transactionId);
    }
}
