package com.notification.Decorator;
import com.notification.Strategy.NotificationStrategy;

public abstract class AbstractNotificationDecorator implements NotificationStrategy{
    public NotificationStrategy strategy;

    public AbstractNotificationDecorator(NotificationStrategy strategy){
        this.strategy = strategy;
    }
    @Override
    public abstract void send(String message);

}