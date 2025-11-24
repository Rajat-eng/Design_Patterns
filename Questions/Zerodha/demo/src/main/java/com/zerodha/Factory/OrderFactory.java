package com.zerodha.Factory;

import java.util.UUID;

import com.zerodha.Enums.OrderType;
import com.zerodha.Enums.TransactionType;
import com.zerodha.Models.Order.BuyOrder;
import com.zerodha.Models.Order.Order;
import com.zerodha.Models.Order.SellOrder;
import com.zerodha.Models.User;
import com.zerodha.Strategy.ExecutionStrategy;
import com.zerodha.Strategy.LimitOrderStrategy;
import com.zerodha.Strategy.MarketOrderStrategy;

public class OrderFactory {
    public static Order create(User user, String instrument, TransactionType tx,
                               OrderType type, int qty, double price) {

        ExecutionStrategy strat = (type == OrderType.MARKET) ? new MarketOrderStrategy() : new LimitOrderStrategy();
        String id = UUID.randomUUID().toString();
        if (tx == TransactionType.BUY) {
            return new BuyOrder(id, user, instrument, type, qty, price, strat);
        } else {
            return new SellOrder(id, user, instrument, type, qty, price, strat);
        }
    }
}
