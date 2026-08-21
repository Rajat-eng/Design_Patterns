package com.amazon.Models;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private final Map<String, CartItem> items = new HashMap<>();

    public synchronized void addItem(Product product, int quantity) {
        if (items.containsKey(product.getId())) {
            items.get(product.getId()).incrementQuantity(quantity);
        } else {
            items.put(product.getId(), new CartItem(product, quantity));
        }
    }

    
    public synchronized void removeItem(String productId) {
        items.remove(productId);
    }

    public synchronized Map<String, CartItem> getItems() {
        // Return a deep snapshot so callers cannot observe in-flight quantity changes.
        Map<String, CartItem> snapshot = new HashMap<>();
        for (Map.Entry<String, CartItem> entry : items.entrySet()) {
            CartItem item = entry.getValue();
            snapshot.put(entry.getKey(), new CartItem(item.getProduct(), item.getQuantity()));
        }
        return Map.copyOf(snapshot);
    }

    public synchronized double calculateTotal() {
        return items.values().stream().mapToDouble(CartItem::getPrice).sum();
    }

    public synchronized void clearCart() {
        items.clear();
    }

    public synchronized boolean isEmpty() {
        return items.isEmpty();
    }
}