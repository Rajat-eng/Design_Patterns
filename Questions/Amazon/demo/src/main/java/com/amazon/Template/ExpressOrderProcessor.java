package com.amazon.Template;

import com.amazon.Models.Order;

public class ExpressOrderProcessor extends OrderProcessingTemplate {
    @Override
    protected void validateOrder(Order order) {
        System.out.println("Validating express order: " + order.getId());
    }

    @Override
    protected void prepareOrder(Order order) {
        System.out.println("Packing express order: " + order.getId());
    }

    @Override
    protected void shipOrder(Order order) {
        System.out.println("Shipping express order: " + order.getId());
    }

    @Override
    protected void notifyCustomer(Order order) {
        System.out.println("Notifying customer for express order: " + order.getId());
    }
}
