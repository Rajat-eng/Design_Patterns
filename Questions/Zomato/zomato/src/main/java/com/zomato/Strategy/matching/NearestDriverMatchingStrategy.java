package com.zomato.Strategy.matching;

import java.util.Comparator;
import java.util.List;

import com.zomato.Models.DeliveryAgent;
import com.zomato.Models.Restaurant;

public class NearestDriverMatchingStrategy implements DriverMatchingStrategy {

    @Override
    public DeliveryAgent findDrivers(List<DeliveryAgent> agents, Restaurant restaurant){
        return agents.stream()
            .filter(agent -> agent.isAvailable())
            .sorted(Comparator.comparingDouble(agent -> restaurant.getAddress().distanceTo(agent.getCurrentLocation())))
            .findFirst().orElse(null);
    }
}
