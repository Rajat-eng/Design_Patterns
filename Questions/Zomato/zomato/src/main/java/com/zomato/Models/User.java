package com.zomato.Models;

import java.util.UUID;

import com.zomato.Tracking.OrderObserver;

public abstract class User implements OrderObserver {
    private String userId;
    private String name;
    private String phoneNumber;

    public User(String name, String phoneNumber) {
        this.userId = UUID.randomUUID().toString();
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getContact() { return phoneNumber; }

    @Override
    public abstract void onUpdate(Order order);
    
}
