package com.zomato.Strategy.matching;

import java.util.List;


import com.zomato.Models.DeliveryAgent;
import com.zomato.Models.Restaurant;

public interface DriverMatchingStrategy {
    public DeliveryAgent findDrivers(List<DeliveryAgent> agents, Restaurant restaurant);
}
