package com.oyo.Models;

import com.oyo.Observer.BookingObserver;


public class Guest implements BookingObserver {
    private final String id;
    private final String name;
    private final String email;

    public Guest(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public void update(Booking booking){
        System.out.println("Booking updated");
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}
