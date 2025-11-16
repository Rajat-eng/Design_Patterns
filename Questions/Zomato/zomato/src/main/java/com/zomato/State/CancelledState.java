package com.zomato.State;

import com.zomato.Enums.OrderStatus;
import com.zomato.Models.Order;

public class CancelledState extends OrderState {
    @Override
    public void processOrder(Order order) {
        System.out.println("Order is cancelled and cannot be processed.");
    }

    @Override
    public void deliverOrder(Order order) {
        System.out.println("Order is cancelled and cannot be delivered.");
    }

    @Override
    public void cancelOrder(Order order) {
        System.out.println("Order is already cancelled.");
    }

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.CANCELLED;
    }
    
}
