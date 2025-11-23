package com.notification.Strategy;

import com.notification.Adapter.ProviderAdapter;

public class SMSNotificationStrategy implements NotificationStrategy {
    private final ProviderAdapter adapter;

    public SMSNotificationStrategy(ProviderAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void send(String message) {
        adapter.send(message);
    }
}
