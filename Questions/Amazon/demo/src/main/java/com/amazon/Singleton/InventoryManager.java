package com.amazon.Singleton;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private static volatile InventoryManager instance;
    private final Map<String, Integer> stockMap = new HashMap<>();

    private InventoryManager() {}

    public static InventoryManager getInstance() {
        if (instance == null) {
            synchronized (InventoryManager.class) {
                if (instance == null) {
                    instance = new InventoryManager();
                }
            }
        }
        return instance;
    }

    public void addStock(String productId, int quantity) {
        stockMap.put(productId, stockMap.getOrDefault(productId, 0) + quantity);
    }

    public int getStock(String productId) {
        return stockMap.getOrDefault(productId, 0);
    }
}
