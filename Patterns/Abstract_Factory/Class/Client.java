package Patterns.Abstract_Factory.Class;

import Patterns.Abstract_Factory.Interface.InvoiceGenerator;
import Patterns.Abstract_Factory.Interface.PaymentGatewayFactory;
import Patterns.Abstract_Factory.Interface.PaymentProcessor;
import Patterns.Abstract_Factory.Interface.RefundService;

public class Client {
    private final PaymentProcessor paymentProcessor;
    private final RefundService refundService;
    private final InvoiceGenerator invoiceGenerator;

    public Client(PaymentGatewayFactory paymentGatewayFactory) {
        this.paymentProcessor = paymentGatewayFactory.createPaymentProcessor();
        this.refundService = paymentGatewayFactory.createRefundService();
        this.invoiceGenerator = paymentGatewayFactory.createInvoiceGenerator();
    }

    public void processPayment(double amount, String transactionId) {
        if (paymentProcessor != null) {
            paymentProcessor.pay(amount);
        }

        if (invoiceGenerator != null) {
            invoiceGenerator.generateInvoice(transactionId);
        }

        if (refundService != null) {
            refundService.refund(transactionId);
        }
    }
}
