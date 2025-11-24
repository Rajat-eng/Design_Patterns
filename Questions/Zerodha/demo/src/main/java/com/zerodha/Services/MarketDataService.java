package com.zerodha.Services;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Simple in-memory simulated LTP (last traded price).
 */
public class MarketDataService {
    private final ConcurrentMap<String, Double> map = new ConcurrentHashMap<>();

    public void setLTP(String instrument, double price) { map.put(instrument, price); }
    public double getLTP(String instrument) { return map.getOrDefault(instrument, 100.0); }
}
