package com.zomato.Services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zomato.Models.DeliveryAgent;
import com.zomato.Models.Restaurant;
import com.zomato.Strategy.matching.DriverMatchingStrategy;
import com.zomato.Strategy.matching.NearestDriverMatchingStrategy;

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
        DriverMatchingStrategy  strategy = new NearestDriverMatchingStrategy();
        return strategy.findDrivers(new ArrayList<>(agents.values()), restaurant); 
    }


}
