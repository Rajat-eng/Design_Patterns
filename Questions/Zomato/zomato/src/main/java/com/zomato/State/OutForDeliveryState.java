package com.zomato.State;

import com.zomato.Enums.OrderStatus;
import com.zomato.Models.Order;

public class OutForDeliveryState extends OrderState {
    @Override
    public void processOrder(Order order) {
        System.out.println("Order is already out for delivery.");
    }

    @Override
    public void deliverOrder(Order order) {
        System.out.println("Order is on its way.");
        order.setState(new DeliveredState());
    }

    @Override
    public void cancelOrder(Order order) {
        System.out.println("Order cannot be cancelled as it is out for delivery.");
    }

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.OUT_FOR_DELIVERY;
    }
    
}
