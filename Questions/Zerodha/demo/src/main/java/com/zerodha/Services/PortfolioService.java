package com.zerodha.Services;

import com.zerodha.Models.User;
import java.util.Map;

public class PortfolioService {
    public Map<String, Integer> getHoldings(User user) {
        return user.portfolio.snapshot();
    }
}
