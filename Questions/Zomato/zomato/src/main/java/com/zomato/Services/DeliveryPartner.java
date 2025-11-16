package com.zomato.Services;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zomato.Models.DeliveryAgent;
import com.zomato.Models.Restaurant;

public class DeliveryPartner {
    private final static DeliveryPartner instance = new DeliveryPartner();
    
    Map<String,DeliveryAgent> agents = new ConcurrentHashMap<>();

    public DeliveryAgent findAgentById(String agentId) {
        return agents.get(agentId);
    }

    public static DeliveryPartner getInstance() {
        return instance;
    }

    public void addAgent(DeliveryAgent agent) {
        agents.put(agent.getUserId(), agent);
    }

    public DeliveryAgent findBestAgent(Restaurant restaurant) {
        return agents.values().stream()
            .filter(agent -> agent.isAvailable())
            .sorted(Comparator.comparingDouble(agent -> restaurant.getAddress().distanceTo(agent.getCurrentLocation())))
            .findFirst().orElse(null);
    }


}
