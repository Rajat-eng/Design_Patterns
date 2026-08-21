package com.amazon.Facade;

import com.amazon.Models.Customer;
import com.amazon.Models.Order;
import com.amazon.Models.ShoppingCart;
import com.amazon.Services.OrderService;
import com.amazon.Services.PaymentService;
import com.amazon.Strategy.PaymentStrategy;

public class CheckoutFacade {
    private final PaymentService paymentService;
    private final OrderService orderService;

    public CheckoutFacade(PaymentService paymentService, OrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    public Order checkout(Customer customer, ShoppingCart cart, PaymentStrategy strategy) {
        double total = cart.calculateTotal();
        boolean paymentDone = paymentService.processPayment(strategy, total);
        if (!paymentDone) {
            System.out.println("Payment failed");
            return null;
        }

        Order order = orderService.createOrder(customer, cart);
        cart.clearCart();
        return order;
    }
}
