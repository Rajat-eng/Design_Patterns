package com.zerodha.Services.Exchange;

import com.zerodha.Enums.ExchangeType;
import com.zerodha.Models.Order.Order;

public interface ExchangeRouter {
    void submit(Order order);
    void cancel(Order order);
    void shutdown();
    ExchangeType getExchangeType();
}

