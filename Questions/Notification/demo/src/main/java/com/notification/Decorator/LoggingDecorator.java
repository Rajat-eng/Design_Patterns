package com.notification.Decorator;

import com.notification.Strategy.NotificationStrategy;

public class LoggingDecorator extends AbstractNotificationDecorator {
   
    public LoggingDecorator(NotificationStrategy strategy) {
        super(strategy);
    }

    @Override
    public void send(String message) {
        System.out.println("[LOG] Sending...");
        strategy.send(message);
        System.out.println("[LOG] Done.");
    }
}
