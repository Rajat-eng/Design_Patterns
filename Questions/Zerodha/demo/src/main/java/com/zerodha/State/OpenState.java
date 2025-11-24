package com.zerodha.State;

import com.zerodha.Enums.OrderStatus;
import com.zerodha.Models.Order.Order;

public class OpenState implements OrderState {
    @Override
    public void onEnter(Order order) {
        order.setStatus(OrderStatus.OPEN);
    }

    @Override
    public void cancel(Order order) {
        order.setStatus(OrderStatus.CANCELLED);
        order.setState(new CancelledState());
        order.onStatusChange();
    }
}
