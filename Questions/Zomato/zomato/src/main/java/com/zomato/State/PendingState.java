package com.zomato.State;

import com.zomato.Enums.OrderStatus;
import com.zomato.Enums.PaymentStatus;
import com.zomato.Enums.PaymentType;
import com.zomato.Models.Order;
import com.zomato.Models.Payment;
import com.zomato.Strategy.OnlinePaymentStrategy;

public class PendingState extends OrderState {
   
    @Override
    public void processOrder(Order order) {
        double amount = order.calculateTotalAmount();
        if(order.getPaymentStrategy() instanceof OnlinePaymentStrategy){
            System.out.println("Retrying online payment for Order: " + order.getId());
            Payment payment = transactionService.getPaymentDetails(order);
            if(payment != null && payment.getStatus() == PaymentStatus.FAILED){
                System.out.println("Previous payment failed. Retrying...");
            }
            order.setPaymentType(PaymentType.CASH);  
        }
        transactionService.initiatePayment(order, amount, order.getPaymentStrategy());
        System.out.println("Order is now being prepared.");
    }

    @Override
    public void deliverOrder(Order order) {
        System.out.println("Order is still pending and cannot be delivered.");
    }

    @Override
    public void cancelOrder(Order order) {
        transactionService.processRefund(order);
        order.setState(new CancelledState());
        order.getDeliveryAgent().setAvailable(true);
        System.out.println("Order has been cancelled and refund processed.");
    }

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.PENDING;
    }
}
