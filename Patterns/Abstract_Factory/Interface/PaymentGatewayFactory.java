package Patterns.Abstract_Factory.Interface;

public interface PaymentGatewayFactory {
    PaymentProcessor createPaymentProcessor();
    RefundService createRefundService();
    InvoiceGenerator createInvoiceGenerator();
}
