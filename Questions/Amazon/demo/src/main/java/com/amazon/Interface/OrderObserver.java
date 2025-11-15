package com.amazon.Interface;

import com.amazon.Models.Order;

public interface OrderObserver {
    void update(Order order);
}
