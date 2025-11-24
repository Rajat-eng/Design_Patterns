package com.zerodha.Strategy;

import com.zerodha.Models.Order.Order;
import com.zerodha.Services.SymbolMatchingEngine;


public interface ExecutionStrategy {
     void execute(Order order, SymbolMatchingEngine engine);
}
