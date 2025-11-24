package com.zerodha.Models.Order;

import com.zerodha.Enums.*;
import com.zerodha.Exceptions.InsufficientFundsException;
import com.zerodha.Models.Stock;
import com.zerodha.Models.User;
import com.zerodha.Strategy.ExecutionStrategy;
import com.zerodha.State.FailedState;
import com.zerodha.State.FilledState;

public class BuyOrder extends Order {
    public BuyOrder(String id, User user, String instrument, OrderType type, int qty, double price, ExecutionStrategy strategy) {
        super(id, user, instrument, Side.BUY, type, qty, price, strategy);
    }

    /**
     * For ad-hoc direct execution only (matching engine handles fills normally).
     */
    @Override
    public String shortDesc() {
        return String.format("Buy[%s] %d@%s rem=%d", orderId.substring(0,6), originalQty,
                type==OrderType.MARKET ? "MKT" : String.format("%.2f", limitPrice), remainingQty);
    }

    // Optional helper if someone wants to "execute" immediately
    public void executeImmediately(double executionPrice) {
        double cost = originalQty * executionPrice;
        boolean ok = user.account.reserve(cost);
        if (!ok) {
            this.setStatus(OrderStatus.FAILED);
            this.setState(new FailedState());
            throw new InsufficientFundsException("Insufficient funds for immediate buy");
        }
        user.portfolio.add(instrument, originalQty);
        this.remainingQty = 0;
        this.setStatus(OrderStatus.FILLED);
        this.setState(new FilledState());
    }
}
