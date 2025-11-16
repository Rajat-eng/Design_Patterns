package com.zomato.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.zomato.Enums.PaymentType;
import com.zomato.Factory.PaymentFactory;
import com.zomato.State.OrderState;
import com.zomato.State.PendingState;
import com.zomato.Strategy.PaymentStrategy;
import com.zomato.Tracking.OrderObserver;

public class Order {
    private final String id;
    private final Customer customer;
    private final Restaurant restaurant;
    private final List<OrderItem> items;
    private OrderState state;
    private DeliveryAgent deliveryAgent;
    private PaymentType paymentType;
    private final List<OrderObserver> observers = new ArrayList<>();

    public Order(Customer customer, Restaurant restaurant, List<OrderItem> items, PaymentType paymentType) {
        this.id = UUID.randomUUID().toString();
        this.customer = customer;
        this.restaurant = restaurant;
        this.items = items;
        addObserver(customer);
        addObserver(restaurant);
        this.paymentType = paymentType;
        setState(new PendingState());
    }

    public double calculateTotalAmount() {
        return items.stream().mapToDouble(o->o.getItem().getPrice() * o.getQuantity()).sum();
    }

    public PaymentStrategy getPaymentStrategy() {
        return PaymentFactory.getPaymentStrategy(this.paymentType);
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getId() { return id; }
    public Customer getCustomer() { return customer; }
    public Restaurant getRestaurant() { return restaurant; }
    public List<OrderItem> getItems() { return items; }
    public DeliveryAgent getDeliveryAgent() { return deliveryAgent; }

    public void setState(OrderState state) {
        this.state = state;
        notifyObservers();
    }

    public OrderState getState() { return state; }

    public void setDeliveryAgent(DeliveryAgent deliveryAgent) {
        this.deliveryAgent = deliveryAgent;
        addObserver(deliveryAgent);
        this.deliveryAgent.setAvailable(false);
    }

    private void addObserver(OrderObserver observer) { observers.add(observer); }
    private void removeObserver(OrderObserver observer) { observers.remove(observer); }
    private void notifyObservers() { observers.forEach(o -> o.onUpdate(this)); }
}
