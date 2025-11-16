package com.amazon.State;


import com.amazon.Models.Order;

public interface OrderState {
    void ship(Order order);
    void deliver(Order order);
    void cancel(Order order);
}