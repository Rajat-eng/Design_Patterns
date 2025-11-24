package com.zerodha.Models;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {
private final Map<String, Integer> holdings = new HashMap<>();

    public synchronized void add(String symbol, int qty) {
        if (qty <= 0) return;
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + qty);
    }

    public synchronized boolean remove(String symbol, int qty) {
        int have = holdings.getOrDefault(symbol, 0);
        if (have < qty) return false;
        if (have == qty) holdings.remove(symbol);
        else holdings.put(symbol, have - qty);
        return true;
    }

    public synchronized int qty(String symbol) {
        return holdings.getOrDefault(symbol, 0);
    }

    public synchronized Map<String, Integer> snapshot() {
        return new HashMap<>(holdings);
    }

    @Override
    public synchronized String toString() {
        return snapshot().toString();
    }
}
