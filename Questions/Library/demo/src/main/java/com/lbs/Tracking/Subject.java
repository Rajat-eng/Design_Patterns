package com.lbs.Tracking;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    public List<OrderObserver> observers = new ArrayList<>();
    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }
    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }
    public void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.update();
        }
    }

    public boolean hasObservers() {
        return !observers.isEmpty();
    }

    public boolean contains(OrderObserver observer) {
        return observers.contains(observer);
    }
    
}
