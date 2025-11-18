package com.bms.Models;

import java.util.UUID;

import com.bms.Observer.MovieObserver;

public class User implements MovieObserver {
 private final String id;
    private final String name;
    private final String email;

    public User(String name, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void update(Movie movie){
        System.out.println(movie.getTitle());
    }
}
