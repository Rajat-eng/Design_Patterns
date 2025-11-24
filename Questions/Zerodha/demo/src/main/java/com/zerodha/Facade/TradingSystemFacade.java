package com.zerodha.Facade;

import java.util.Map;

import com.zerodha.Builder.OrderBuilder;
import com.zerodha.Enums.ExchangeType;
import com.zerodha.Factory.ExchangeRouterFactory;
import com.zerodha.Models.Order.Order;
import com.zerodha.Models.User;
import com.zerodha.Services.MarketDataService;
import com.zerodha.Services.OrderService;
import com.zerodha.Services.PortfolioService;
import com.zerodha.Services.TradeService;
import com.zerodha.Services.Exchange.BSEExchangeRouter;
import com.zerodha.Services.Exchange.ExchangeRouter;
import com.zerodha.Services.Exchange.NSEExchangeRouter;

public class TradingSystemFacade {

    private final MarketDataService marketData;
    private final TradeService tradeService;
    private final PortfolioService portfolioService;

    private final ExchangeRouterFactory exchangeFactory;
    private final OrderService orderService;

    public TradingSystemFacade() {
        this.marketData = new MarketDataService();
        this.tradeService = new TradeService();
        this.portfolioService = new PortfolioService();

        // Create all exchange routers
        ExchangeRouter nse = new NSEExchangeRouter(tradeService);
        ExchangeRouter bse = new BSEExchangeRouter(tradeService);

        // Factory auto registers them
        this.exchangeFactory = new ExchangeRouterFactory(
                java.util.List.of(nse, bse)
        );

        this.orderService = new OrderService(exchangeFactory, marketData);
    }

    // ---------- User + Portfolio ----------
    public User createUser(String name, double balance) {
        return new User("u-" + System.nanoTime(), name, balance);
    }

    public void addHolding(User user, String inst, int qty) {
        user.portfolio.add(inst, qty);
    }

    public double getBalance(User user) {
        return user.account.getBalance();
    }

    public Map<String, Integer> getPortfolio(User user) {
        return portfolioService.getHoldings(user);
    }

    // ---------- Market Data ----------
    public void setMarketPrice(String inst, double price) {
        marketData.setLTP(inst, price);
    }

    // ---------- Order Placement ----------
    public boolean placeMarketBuy(User user, String inst, int qty, ExchangeType exch) {
        Order order = new OrderBuilder()
                .forUser(user)
                .withInstrument(inst)
                .buy(qty)
                .market()
                .build();

        return orderService.placeOrder(order, exch);
    }

    public boolean placeLimitBuy(User user, String inst, int qty, double price, ExchangeType exch) {
        Order order = new OrderBuilder()
                .forUser(user)
                .withInstrument(inst)
                .buy(qty)
                .limit(price)
                .build();

        return orderService.placeOrder(order, exch);
    }

    public boolean placeLimitSell(User user, String inst, int qty, double price, ExchangeType exch) {
        Order order = new OrderBuilder()
                .forUser(user)
                .withInstrument(inst)
                .sell(qty)
                .limit(price)
                .build();

        return orderService.placeOrder(order, exch);
    }

    public void cancelOrder(Order order, ExchangeType exch) {
        orderService.cancelOrder(order, exch);
    }

    // ---------- Shutdown ----------
    public void shutdown() {
        exchangeFactory.getRouter(ExchangeType.NSE).shutdown();
        exchangeFactory.getRouter(ExchangeType.BSE).shutdown();
        System.out.println("[Facade] shutdown");
    }
}
