package com.zomato.State;

import com.zomato.Enums.OrderStatus;
import com.zomato.Models.Order;
import com.zomato.Services.DeliveryPartner;
import com.zomato.Services.TransactionService;

public abstract class OrderState {
    public final TransactionService transactionService = TransactionService.getInstance();
    public final DeliveryPartner deliveryPartnerService = DeliveryPartner.getInstance();
    public abstract void processOrder(Order order);
    public abstract void pickupOrder(Order order);
    public abstract void deliverOrder(Order order);
    public abstract void cancelOrder(Order order);
    public abstract OrderStatus getStatus();
}
