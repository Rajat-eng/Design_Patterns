package com.zerodha.Strategy;

import com.zerodha.Models.Order.Order;
import com.zerodha.Services.SymbolMatchingEngine;

public class LimitOrderStrategy implements ExecutionStrategy{
    @Override
    public void execute(Order incoming, SymbolMatchingEngine engine) {
        while (incoming.getRemainingQty() > 0) {
            // if order is buying then accept lowest sell
            Order bestOpp = engine.peekOpposite(incoming); 
            if (bestOpp == null) break;
            double oppPrice = engine.getOppositePrice(bestOpp);
            if (!priceMatches(incoming, oppPrice)) break;
            int traded = Math.min(incoming.getRemainingQty(), bestOpp.getRemainingQty());
            engine.executeTrade(incoming, bestOpp, traded, oppPrice);
            if (bestOpp.getRemainingQty() == 0) engine.removeOrder(bestOpp);
        }
    }

    private boolean priceMatches(Order inc, double oppPrice) {
        // Buy order --> accept price from seeling order which is close to my buy price
        if (inc.isBuy()) return inc.getLimitPrice() >= oppPrice;
        else return inc.getLimitPrice() <= oppPrice; 
        // Sell Order -->buying from person which gived me highest price
    }
}
