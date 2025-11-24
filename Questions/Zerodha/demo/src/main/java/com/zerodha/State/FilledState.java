package com.zerodha.State;

import com.zerodha.Enums.OrderStatus;
import com.zerodha.Models.Order.Order;

public class FilledState implements OrderState {
    @Override
    public void onEnter(Order order) {
        order.setStatus(OrderStatus.FILLED);
        order.onStatusChange();
    }

    @Override
    public void cancel(Order order) {
        // no-op
    }
}
