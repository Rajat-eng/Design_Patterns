package com.zerodha.Observer;

import com.zerodha.Models.Stock;

public interface StockObserver {
    void update(Stock stock);
}
