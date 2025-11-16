package com.uber.State;

import com.uber.Enums.TripStatus;
import com.uber.Models.Driver;
import com.uber.Models.Trip;

public class RequestedState implements TripState{
     @Override
    public void request(Trip trip) {
        System.out.println("Trip is already in requested state.");
    }

    @Override
    public void assign(Trip trip, Driver driver) {
        trip.setDriver(driver);
        trip.setStatus(TripStatus.ASSIGNED);
        trip.setState(new AssignedState());
    }

    @Override
    public void start(Trip trip) {
        System.out.println("Cannot start a trip that has not been assigned a driver.");
    }

    @Override
    public void end(Trip trip) {
        System.out.println("Cannot end a trip that has not been assigned a driver.");
    }
}
