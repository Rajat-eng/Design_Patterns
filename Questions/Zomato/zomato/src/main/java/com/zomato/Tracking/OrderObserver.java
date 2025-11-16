package com.zomato.Tracking;

import com.zomato.Enums.OrderStatus;
import com.zomato.Models.Order;

public interface OrderObserver {
    void onUpdate(Order order);
}
