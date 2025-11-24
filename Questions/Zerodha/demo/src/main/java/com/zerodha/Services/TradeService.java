package com.zerodha.Services;

import com.zerodha.Models.Trade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Records trades in memory. Thread-safe.
 */
public class TradeService {
    private final List<Trade> trades = Collections.synchronizedList(new ArrayList<>());

    public void record(Trade t) {
        trades.add(t);
        System.out.println("[TradeService] Recorded trade: " + t);
    }

    public List<Trade> snapshot() {
        synchronized (trades) {
            return new ArrayList<>(trades);
        }
    }
}
