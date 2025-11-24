package com.zerodha.State;

import com.zerodha.Models.Order.Order;

public interface OrderState {
    void onEnter(Order order);
    void cancel(Order order);
}
