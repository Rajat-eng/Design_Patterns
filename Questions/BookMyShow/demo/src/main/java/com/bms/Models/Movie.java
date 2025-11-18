package com.bms.Models;

import com.bms.Observer.MovieObserver;
import com.bms.Observer.MovieSubject;

public class Movie extends MovieSubject{
    private final String id;
    private final String title;
    private final int durationInMinutes;

    public Movie(String id, String title, int durationInMinutes) {
        this.id = id;
        this.title = title;
        this.durationInMinutes = durationInMinutes;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void notifyObservers(){
        for(MovieObserver observer: super.getObservers()){
            observer.update(this);
        }
    }
    
}
