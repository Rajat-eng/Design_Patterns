package com.amazon.Builder;

import java.util.ArrayList;
import java.util.List;

import com.amazon.Models.Address;
import com.amazon.Models.Customer;
import com.amazon.Models.Order;
import com.amazon.Models.OrderLineItem;

public class OrderBuilder {
    private Customer customer;
    private Address shippingAddress;
    private final List<OrderLineItem> items = new ArrayList<>();
    private double totalAmount;

    public OrderBuilder setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public OrderBuilder setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public OrderBuilder addItem(OrderLineItem item) {
        this.items.add(item);
        return this;
    }

    public OrderBuilder totalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public Order build() {
        if (customer == null) {
            throw new IllegalStateException("Customer is required to build an order");
        }
        if (shippingAddress == null) {
            throw new IllegalStateException("Shipping address is required to build an order");
        }
        return new Order(customer, items, shippingAddress, totalAmount);
    }
}
