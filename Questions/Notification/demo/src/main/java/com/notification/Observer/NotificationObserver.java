package com.notification.Observer;

import com.notification.Strategy.NotificationStrategy;

public abstract class NotificationObserver {
    private final NotificationStrategy strategy;

    public NotificationObserver(NotificationStrategy strategy) {
        this.strategy = strategy;
    }

    public void update(String message) {
        strategy.send(message);
    }
}
