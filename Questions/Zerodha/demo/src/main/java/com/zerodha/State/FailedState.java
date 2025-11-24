package com.zerodha.State;

import com.zerodha.Enums.OrderStatus;
import com.zerodha.Models.Order.Order;

public class FailedState implements OrderState {
     @Override
    public void onEnter(Order order) {
        order.setStatus(OrderStatus.FAILED);
        order.onStatusChange();
    }

    @Override
    public void cancel(Order order) { /* no-op */ }
}
