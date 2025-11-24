package com.zerodha.State;

import com.zerodha.Models.Order.Order;

public class CancelledState implements OrderState {
     @Override
    public void onEnter(Order order) { /* already set by caller */ }

    @Override
    public void cancel(Order order) { /* already cancelled */ }
}
