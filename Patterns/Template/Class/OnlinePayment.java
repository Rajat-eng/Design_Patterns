package Patterns.Template.Class;

public abstract class OnlinePayment {
    public final void processPayment() {
        authenticate();
        validateFunds();
        processTransaction();
        sendNotification();
    }

    // Common Step (Implemented)
    private void validateFunds() {
        System.out.println("Validating funds...");
    }

    // Common Step (Implemented)
    private void sendNotification() {
        System.out.println("Sending payment confirmation...");
    }

    // Steps that must be implemented by subclasses
    protected abstract void authenticate();
    protected abstract void processTransaction();
}
