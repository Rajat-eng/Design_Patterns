package com.zomato;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import com.zomato.Enums.OrderStatus;
import com.zomato.Enums.PaymentType;
import com.zomato.Models.Address;
import com.zomato.Models.Customer;
import com.zomato.Models.DeliveryAgent;
import com.zomato.Models.Order;
import com.zomato.Models.OrderItem;
import com.zomato.Models.Restaurant;
import com.zomato.Models.User;
import com.zomato.Services.DeliveryPartner;

public class ZomatoService {
    private static final ZomatoService instance = new ZomatoService();
    private final Map<String, Customer> customers = new ConcurrentHashMap<>();
    private final Map<String, Restaurant> restaurants = new ConcurrentHashMap<>();
    private final Map<String, Order> orders = new ConcurrentHashMap<>();
    public static ZomatoService getInstance() {
        return instance;
    }

    public void registerCustomer(User user) {
        if(user instanceof Customer customer) {
            if (customers.containsKey(customer.getContact())) {
                System.out.println("Customer with contact " + customer.getContact() + " already registered.");
                return;
            }
            customers.put(customer.getUserId(), customer);
        } else if(user instanceof DeliveryAgent deliveryAgent) {
            // Delivery Agent or other user types can be handled here
            DeliveryPartner.getInstance().addAgent(deliveryAgent);
        }
    }

    public Customer getCustomer(String contact) {
        return customers.get(contact);
    }

    public Restaurant registerRestaurant(String name, Address address) {
        Restaurant restaurant = new Restaurant(name, address);
        restaurants.put(restaurant.getId(), restaurant);
        return restaurant;
    }
    public DeliveryAgent getDeliveryAgent(String agentId) {
        return DeliveryPartner.getInstance().findAgentById(agentId);
    }

     public Order placeOrder(String customerId, String restaurantId, List<OrderItem> items) {
        Customer customer = customers.get(customerId);
        Restaurant restaurant = restaurants.get(restaurantId);
        if (customer == null || restaurant == null) throw new NoSuchElementException("Customer or Restaurant not found.");

        Order order = new Order(customer, restaurant, items, PaymentType.ONLINE);
        orders.put(order.getId(), order);
        customer.addOrderToHistory(order);
        order.processOrder();
        System.out.printf("Order %s placed by %s at %s.\n", order.getId(), customer.getName(), restaurant.getName());
        return order;
    }

    public Order pickUpOrder (String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return null;
        }
        order.pickUpOrder();
        return order;
    }
    
    public void deliverOrder (String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        order.deliverOrder();
    }

    public void trackOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        OrderStatus status = order.getState().getStatus();
        System.out.printf("Order %s is currently %s.\n", orderId, status);
    }

    public void cancelOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        order.getState().cancelOrder(order);
    }



}
