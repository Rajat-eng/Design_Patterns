package com.notification.Decorator;

import com.notification.Strategy.NotificationStrategy;

public class RetryDecorator extends AbstractNotificationDecorator {
    private final int attempts;

    public RetryDecorator(NotificationStrategy strategy, int attempts) {
        super(strategy);
        this.attempts = attempts;
    }

    @Override
    public void send(String message) {
        for (int i = 1; i <= attempts; i++) {
            try {
                System.out.println("[Attempt " + i + "]");
                strategy.send(message);
                return;
            } catch (RuntimeException e) {
                System.out.println("Retry failed");
            }
        }
    }
}
