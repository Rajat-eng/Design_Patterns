package com.amazon.Template;

import com.amazon.Models.Order;

public abstract class OrderProcessingTemplate {
    public final void processOrder(Order order) {
        validateOrder(order);
        prepareOrder(order);
        shipOrder(order);
        notifyCustomer(order);
    }

    protected abstract void validateOrder(Order order);
    protected abstract void prepareOrder(Order order);
    protected abstract void shipOrder(Order order);
    protected abstract void notifyCustomer(Order order);
}
