package com.amazon.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazon.Models.*;

public class OrderService {
    private final InventoryService inventoryService;

    public OrderService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public Order createOrder(Customer customer, ShoppingCart cart) {
        Map<String, CartItem> cartSnapshot = cart.getItems();
        List<OrderLineItem> result = new ArrayList<>();
        cartSnapshot.values().stream()
            .map(cartItem -> new OrderLineItem(
                    cartItem.getProduct().getId(),
                    cartItem.getProduct().getName(),
                    cartItem.getQuantity(),
                    cartItem.getProduct().getPrice()))
            .forEach(result::add); // Adding each OrderLineItem to result list

        double totalAmount = cartSnapshot.values().stream().mapToDouble(CartItem::getPrice).sum();

        inventoryService.updateStockForOrder(result);
        // inventory service throws an exception if stock is insufficient, so we don't need to check here 

        return new Order(customer, result, customer.getShippingAddress(), totalAmount);
    }
}
