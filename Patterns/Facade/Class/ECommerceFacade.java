package Patterns.Facade.Class;

public class ECommerceFacade {
    private UserAuthentication userAuthentication;
    private PaymentProcessing paymentProcessing;
    private InventoryManagement inventoryManagement;
    private OrderFulfillment orderFulfillment;

    public ECommerceFacade(UserAuthentication userAuthentication, PaymentProcessing paymentProcessing,
                           InventoryManagement inventoryManagement, OrderFulfillment orderFulfillment) {
        this.userAuthentication = userAuthentication;
        this.paymentProcessing = paymentProcessing;
        this.inventoryManagement = inventoryManagement;
        this.orderFulfillment = orderFulfillment;
    }

    public void purchaseProduct(String username, String password, String paymentMethod, String productId, int quantity) {
        userAuthentication.login(username, password);
        paymentProcessing.processPayment(paymentMethod);
        inventoryManagement.updateInventory(productId, quantity);
        orderFulfillment.fulfillOrder(productId);
        userAuthentication.logout();
    }
}
