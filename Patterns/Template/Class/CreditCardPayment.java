package Patterns.Template.Class;

public class CreditCardPayment extends OnlinePayment {
    @Override
    protected void authenticate() {
        System.out.println("Authenticating using CVV and OTP...");
    }

    @Override
    protected void processTransaction() {
        System.out.println("Processing payment via Credit Card...");
    }  
}
