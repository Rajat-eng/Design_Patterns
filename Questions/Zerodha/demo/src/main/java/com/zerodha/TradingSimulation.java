package com.zerodha;

import com.zerodha.Enums.ExchangeType;
import com.zerodha.Facade.TradingSystemFacade;
import com.zerodha.Models.User;

public class TradingSimulation {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting NSE/BSE simulation...");

        TradingSystemFacade facade = new TradingSystemFacade();

        User alice = facade.createUser("Alice", 10000);
        User bob   = facade.createUser("Bob", 5000);

        // Bob holds shares
        facade.addHolding(bob, "RELIANCE", 100);

        // Market data
        facade.setMarketPrice("RELIANCE", 2500.0);

        // Place orders on NSE
        facade.placeLimitSell(bob, "RELIANCE", 50, 2520.0, ExchangeType.NSE);
        facade.placeLimitSell(bob, "RELIANCE", 20, 2490.0, ExchangeType.NSE);

        // Alice market buy on NSE -> should take best sells
        facade.placeMarketBuy(alice, "RELIANCE", 30, ExchangeType.NSE);

        // Place orders on BSE (independent)
        facade.placeLimitSell(bob, "RELIANCE", 10, 2480.0, ExchangeType.BSE);
        facade.placeLimitBuy(alice, "RELIANCE", 15, 2475.0, ExchangeType.BSE);

        // Wait for processing
        Thread.sleep(2000);

        System.out.println("\n--- Final ---");
        System.out.println("Alice balance: " + facade.getBalance(alice));
        System.out.println("Alice portfolio: " + facade.getPortfolio(alice));
        System.out.println("Bob balance: " + facade.getBalance(bob));
        System.out.println("Bob portfolio: " + facade.getPortfolio(bob));

        facade.shutdown();
        System.out.println("Done.");
    }
}
