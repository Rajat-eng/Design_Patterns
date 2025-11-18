package com.bms.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class MovieSubject {
    private final List<MovieObserver> observers = new ArrayList<>();

    public void addObserver(MovieObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(MovieObserver observer) {
        observers.remove(observer);
    }

    public List<MovieObserver> getObservers(){
        return this.observers;
    }


    public abstract void notifyObservers();
}
