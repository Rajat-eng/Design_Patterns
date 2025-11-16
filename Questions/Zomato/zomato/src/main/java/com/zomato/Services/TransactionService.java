package com.zomato.Services;

import java.util.HashMap;
import java.util.Map;

import com.zomato.Enums.PaymentStatus;
import com.zomato.Models.Order;
import com.zomato.Models.Payment;
import com.zomato.State.CancelledState;
import com.zomato.State.PendingState;
import com.zomato.State.ReadyForPickupState;
import com.zomato.Strategy.PaymentStrategy;

public class TransactionService {

    private final static TransactionService instance = new TransactionService();
    Map<Order,Payment> orderPaymentMap = new HashMap<>();
    public static TransactionService getInstance() {
        return instance;
    }

    // Mock: simulate payment gateway response
    private boolean performGatewayPayment() {
        return Math.random() > 0.2; // 80% success
    }

    public Payment initiatePayment(Order order, double amount , PaymentStrategy paymentStrategy) {
        System.out.println("Payment initiated for Order: " + order.getId());
        
        boolean success = performGatewayPayment();
        Payment payment = paymentStrategy.pay(order, amount);
        if (success) {
            payment.setStatus(PaymentStatus.SUCCESS);
            System.out.println("Payment SUCCESS for Order: " + order.getId());
            order.setState(new ReadyForPickupState());  // move order forward
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            System.out.println("Payment FAILED for Order: " + order.getId());
            order.setState(new PendingState()); // retry allowed
        }

        orderPaymentMap.put(order,payment);
        return payment;
    }

    public void processRefund(Order order) {
        System.out.println("Initiating refund for Order: " + order.getId());
        Payment payment = orderPaymentMap.get(order);
        if (payment == null || payment.getStatus() != PaymentStatus.SUCCESS) {
            System.out.println("No successful payment found for Order: " + order.getId() + ". Refund not possible.");
            return;
        }
        payment.setStatus(PaymentStatus.REFUND_INITIATED);

        // mock refund success
        boolean refundSuccess = Math.random() > 0.1;

        if (refundSuccess) {
            payment.setStatus(PaymentStatus.REFUNDED);
            order.setState(new CancelledState());
            System.out.println("Refund completed for Order: " + order.getId());
        } else {
            System.out.println("Refund failed. Will retry asynchronously.");
        }
    }

    public Payment getPaymentDetails(Order order) {
        return orderPaymentMap.get(order);
    }

}
 
