package com.amazon.Template;

import com.amazon.Models.Order;

public class StandardOrderProcessor extends OrderProcessingTemplate {
    @Override
    protected void validateOrder(Order order) {
        System.out.println("Validating standard order: " + order.getId());
    }

    @Override
    protected void prepareOrder(Order order) {
        System.out.println("Packing standard order: " + order.getId());
    }

    @Override
    protected void shipOrder(Order order) {
        System.out.println("Shipping standard order: " + order.getId());
    }

    @Override
    protected void notifyCustomer(Order order) {
        System.out.println("Notifying customer for standard order: " + order.getId());
    }
}