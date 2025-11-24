package com.zerodha.Factory;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.zerodha.Enums.ExchangeType;
import com.zerodha.Services.Exchange.ExchangeRouter;


public class ExchangeRouterFactory {

    private final Map<ExchangeType, ExchangeRouter> routerMap = new EnumMap<>(ExchangeType.class);

    public ExchangeRouterFactory(List<ExchangeRouter> routers) {
        routers.forEach(r -> routerMap.put(r.getExchangeType(), r));
    }

    public ExchangeRouter getRouter(ExchangeType type) {
        ExchangeRouter router = routerMap.get(type);
        if (router == null)
            throw new IllegalArgumentException("Unsupported exchange: " + type);
        return router;
    }
}

