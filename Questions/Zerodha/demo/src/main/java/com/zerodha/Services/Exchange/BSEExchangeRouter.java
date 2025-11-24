package com.zerodha.Services.Exchange;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.zerodha.Enums.ExchangeType;
import com.zerodha.Models.Order.Order;
import com.zerodha.Services.SymbolMatchingEngine;
import com.zerodha.Services.TradeService;


public class BSEExchangeRouter implements ExchangeRouter {

    private final ConcurrentMap<String, SymbolMatchingEngine> engines = new ConcurrentHashMap<>();
    private final TradeService tradeService;

    public BSEExchangeRouter(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    private SymbolMatchingEngine engineFor(String symbol) {
        return engines.computeIfAbsent(symbol,
            s -> new SymbolMatchingEngine(symbol, tradeService));
    }

    @Override
    public void submit(Order order) {
        engineFor(order.getInstrument()).submit(order);
    }

    @Override
    public void cancel(Order order) {
        engineFor(order.getInstrument()).removeOrder(order);
    }

    @Override
    public void shutdown() {
        engines.values().forEach(SymbolMatchingEngine::shutdown);
    }

    @Override
    public ExchangeType getExchangeType() {
        return ExchangeType.BSE;
    }
}
