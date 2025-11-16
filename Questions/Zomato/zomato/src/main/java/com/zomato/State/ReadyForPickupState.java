package com.zomato.State;

import com.zomato.Enums.OrderStatus;
import com.zomato.Models.DeliveryAgent;
import com.zomato.Models.Order;

public class ReadyForPickupState extends OrderState {
    @Override
    public void processOrder(Order order) {
        DeliveryAgent agent = deliveryPartnerService.findBestAgent(order.getRestaurant());
        order.setDeliveryAgent(agent);
        System.out.println("Order is ready for pickup.");
        order.setState(new OutForDeliveryState());
    }

    @Override
    public void deliverOrder(Order order) {
        System.out.println("Order is picked up by delivery agent.");
    }

    @Override
    public void cancelOrder(Order order) {
        System.out.println("Order cannot be cancelled as it is ready for pickup.");
    }

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.READY_FOR_PICKUP;
    }
}
