package com.notification.Strategy;

import com.notification.Adapter.ProviderAdapter;

public class EmailNotificationStrategy implements NotificationStrategy {
    private final ProviderAdapter adapter;

    public EmailNotificationStrategy(ProviderAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void send(String message) {
        adapter.send(message);
    }
}

