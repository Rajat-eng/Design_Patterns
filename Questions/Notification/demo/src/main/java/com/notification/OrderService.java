package com.notification;

public class OrderService {

    private final NotificationService notificationService;

    public OrderService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void placeOrder(String orderId) {
        System.out.println("Order placed: " + orderId);
        notificationService.notifyAllObservers("Order " + orderId + " placed!");
    }
}
