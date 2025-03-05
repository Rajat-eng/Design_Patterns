package Patterns.Template.Class;

public class PayPalPayment extends OnlinePayment {
    @Override
    protected void authenticate() {
        System.out.println("Authenticating using PayPal credentials...");
    }

    @Override
    protected void processTransaction() {
        System.out.println("Processing payment via PayPal...");
    }
}
