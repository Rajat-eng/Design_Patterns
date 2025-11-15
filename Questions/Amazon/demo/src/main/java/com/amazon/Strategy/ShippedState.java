package com.amazon.Strategy;

import com.amazon.Interface.OrderState;
import com.amazon.Interface.OrderStatus;
import com.amazon.Models.Order;

public class ShippedState implements OrderState {
    @Override
    public void ship(Order order) { System.out.println("Order is already shipped."); }

    @Override
    public void deliver(Order order) {
        System.out.println("Delivering order " + order.getId());
        order.setStatus(OrderStatus.DELIVERED);
        order.setState(new DeliveredState());
    }

    @Override
    public void cancel(Order order) { System.out.println("Cannot cancel a shipped order."); }
} 
