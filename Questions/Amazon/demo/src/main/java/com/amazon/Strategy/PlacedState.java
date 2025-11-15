package com.amazon.Strategy;

import com.amazon.Interface.OrderState;
import com.amazon.Interface.OrderStatus;
import com.amazon.Models.Order;

public class PlacedState implements OrderState {
    @Override
    public void ship(Order order) {
        System.out.println("Shipping order " + order.getId());
        order.setStatus(OrderStatus.SHIPPED);
        order.setState(new ShippedState());
    }

    @Override
    public void deliver(Order order) { System.out.println("Cannot deliver an order that has not been shipped."); }

    @Override
    public void cancel(Order order) {
        System.out.println("Cancelling order " + order.getId());
        order.setStatus(OrderStatus.CANCELLED);
        order.setState(new CancelledState());
    }
}