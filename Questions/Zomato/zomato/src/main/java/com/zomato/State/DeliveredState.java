package com.zomato.State;

import com.zomato.Enums.OrderStatus;
import com.zomato.Models.Order;
import com.zomato.Strategy.CashPaymentStrategy;

public class DeliveredState extends OrderState {
    @Override
    public void processOrder(Order order) {
        System.out.println("Order has already been delivered.");
    }

    @Override
    public void deliverOrder(Order order) {
        double amount = order.calculateTotalAmount();
        if(order.getPaymentStrategy() instanceof CashPaymentStrategy){
             transactionService.initiatePayment(order, amount, order.getPaymentStrategy());
            System.out.println("Order has already been delivered.");
            System.out.println("Collecting payment on delivery for Order: " + order.getId());
        } else {
            System.out.println("Order has already been delivered and paid online.");
        }
    }

    @Override
    public void cancelOrder(Order order) {
        System.out.println("Order has already been delivered and cannot be cancelled.");
    }

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.DELIVERED;
    }
    
}
