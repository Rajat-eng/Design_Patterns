package com.task.Models;

import java.util.UUID;

import com.task.Observer.TaskObserver;

public class User implements TaskObserver {
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

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public void update(Task task, String message) {
        System.out.println("User " + this.name + " notified: Task '" + task.getTitle() + "' updated - " + message);
    }

    @Override
    public void update(User changedBy, Task task, String changeType) {
        System.out.println("User " + this.name + " notified: Task '" + task.getTitle() + "' updated by " + changedBy.getName() + " - " + changeType);
    }
}
