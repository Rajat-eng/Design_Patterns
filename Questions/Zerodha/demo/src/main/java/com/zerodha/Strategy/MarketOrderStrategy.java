package com.zerodha.Strategy;

import com.zerodha.Models.Order.Order;
import com.zerodha.Services.SymbolMatchingEngine;


public class MarketOrderStrategy implements ExecutionStrategy {
    @Override
    public void execute(Order incoming, SymbolMatchingEngine engine) {
        while (incoming.getRemainingQty() > 0) {
            Order bestOpp = engine.peekOpposite(incoming);
            if (bestOpp == null) break;
            int traded = Math.min(incoming.getRemainingQty(), bestOpp.getRemainingQty());
            double tradePrice = engine.getOppositePrice(bestOpp);
            engine.executeTrade(incoming, bestOpp, traded, tradePrice);
            if (bestOpp.getRemainingQty() == 0) engine.removeOrder(bestOpp);
        }
        // If still remaining, will be handled by engine (fail or add to book)
    }
}
