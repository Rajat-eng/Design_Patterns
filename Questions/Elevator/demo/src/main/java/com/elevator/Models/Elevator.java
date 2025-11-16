package com.elevator.Models;

import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import com.elevator.Enums.Direction;
import com.elevator.State.ElevatorState;
import com.elevator.State.IdleState;

public class Elevator implements Runnable {
    private final int id;
    private AtomicInteger currentFloor;
    private ElevatorState state;
    private volatile boolean isRunning = true;

    private final TreeSet<Integer> upRequests;
    private final TreeSet<Integer> downRequests;

    public Elevator(int id) {
        this.id = id;
        this.currentFloor = new AtomicInteger(1);
        this.state = new IdleState();
        this.upRequests = new TreeSet<>();
        this.downRequests = new TreeSet<>((a, b) -> b - a); // Descending order for down requests
    }

    public int getId() {
        return id;
    }
    public int getCurrentFloor() {
        return currentFloor.get();
    }

    public void setCurrentFloor(int floor) {
        this.currentFloor.set(floor);
    }

    public void move() {
        state.move(this);
    }

    public void addRequest(Request request) {
        state.addRequest(this, request);
    }

    public void setState(ElevatorState state) {
        this.state = state;
    }

    public Direction getDirection() { return state.getDirection(); }
    public TreeSet<Integer> getUpRequests() { return upRequests; }
    public TreeSet<Integer> getDownRequests() { return downRequests; }
    public boolean isRunning() { return isRunning; }
    public void stopElevator() { this.isRunning = false; }

    @Override
    public void run() {
        while (isRunning) {
            move();
            try {
                Thread.sleep(1000); // Simulate movement time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                isRunning = false;
            }
        }
    }
}
