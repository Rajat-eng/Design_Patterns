package Patterns.Abstract_Factory.Class;

import Patterns.Abstract_Factory.Interface.InvoiceGenerator;
import Patterns.Abstract_Factory.Interface.PaymentGatewayFactory;
import Patterns.Abstract_Factory.Interface.PaymentProcessor;
import Patterns.Abstract_Factory.Interface.RefundService;

public class StripeFactory implements PaymentGatewayFactory {
    @Override
    public PaymentProcessor createPaymentProcessor() {
        return new StripePaymentProcessor();
    }

    @Override
    public RefundService createRefundService() {
        return new StripeRefundService();
    }

    @Override
    public InvoiceGenerator createInvoiceGenerator() {
        return new StripeInvoiceGenerator();
    }
}
