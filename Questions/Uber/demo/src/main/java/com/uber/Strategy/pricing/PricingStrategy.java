package com.uber.Strategy.pricing;

import com.uber.Enums.RideType;
import com.uber.Models.Location;

public interface PricingStrategy {
    double calculateFare(Location pickup, Location dropoff, RideType rideType);
}
