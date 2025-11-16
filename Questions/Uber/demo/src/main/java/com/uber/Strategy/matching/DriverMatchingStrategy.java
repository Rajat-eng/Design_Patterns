package com.uber.Strategy.matching;

import java.util.List;

import com.uber.Enums.RideType;
import com.uber.Models.Driver;
import com.uber.Models.Location;

public interface DriverMatchingStrategy {
    List<Driver> findDrivers(List<Driver> allDrivers, Location pickupLocation, RideType rideType);
}
