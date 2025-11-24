package com.zerodha.Models.Order;

import com.zerodha.Enums.*;
import com.zerodha.Exceptions.InsufficientFundsException;
import com.zerodha.State.FailedState;
import com.zerodha.Strategy.ExecutionStrategy;

public class SellOrder extends Order {
    public SellOrder(String id, com.zerodha.Models.User user, String instrument, OrderType type, int qty, double price, ExecutionStrategy strategy) {
        super(id, user, instrument, Side.SELL, type, qty, price, strategy);
    }

    @Override
    public String shortDesc() {
        return String.format("Sell[%s] %d@%s rem=%d", orderId.substring(0,6), originalQty,
                type==OrderType.MARKET ? "MKT" : String.format("%.2f", limitPrice), remainingQty);
    }

    public void reserveHoldingsForSell() {
        boolean ok = user.portfolio.remove(instrument, originalQty);
        if (!ok) {
            this.setStatus(OrderStatus.FAILED);
            this.setState(new FailedState());
            throw new InsufficientFundsException("Not enough holdings to place sell order");
        }
    }
}
