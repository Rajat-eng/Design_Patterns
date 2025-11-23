package com.notification;

import java.util.ArrayList;
import java.util.List;

import com.notification.Observer.NotificationObserver;

public class NotificationService {

    private final List<NotificationObserver> observers = new ArrayList<>();

    public void addObserver(NotificationObserver observer) {
        observers.add(observer);
    }

    public void notifyAllObservers(String message) {
        for (NotificationObserver observer : observers) {
            observer.update(message);
        }
    }
}
