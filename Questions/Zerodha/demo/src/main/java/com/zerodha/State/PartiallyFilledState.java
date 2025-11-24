package com.zerodha.State;

import com.zerodha.Enums.OrderStatus;
import com.zerodha.Models.Order.Order;

public class PartiallyFilledState implements OrderState {
    @Override
    public void onEnter(Order order) {
        order.setStatus(OrderStatus.PARTIALLY_FILLED);
        order.onStatusChange();
    }

    @Override
    public void cancel(Order order) {
        order.setStatus(OrderStatus.CANCELLED);
        order.setState(new CancelledState());
        order.onStatusChange();
    }
}
