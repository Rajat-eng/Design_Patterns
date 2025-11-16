package com.zomato;

import java.util.List;

import com.zomato.Models.Address;
import com.zomato.Models.Customer;
import com.zomato.Models.DeliveryAgent;
import com.zomato.Models.MenuItem;
import com.zomato.Models.Order;
import com.zomato.Models.OrderItem;
import com.zomato.Models.Restaurant;

public class Main {
    public static void main(String[] args) {
        ZomatoService service = ZomatoService.getInstance();

        Address aliceAddress = new Address("123 Maple St", "Springfield", "12345", 40.7128, -74.0060);
        Address pizzaAddress = new Address("456 Oak Ave", "Springfield", "12345", 40.7138, -74.0070);
        Address burgerAddress = new Address("789 Pine Ln", "Springfield", "12345", 40.7108, -74.0050);
        Address tacoAddress = new Address("101 Elm Ct", "Shelbyville", "54321", 41.7528, -75.0160);

        Customer alice = new Customer("Alice", "123-4567-890", aliceAddress);
        service.registerCustomer(alice);
        DeliveryAgent Bob = new DeliveryAgent("Bob", "321-4567-880", new Address("1 B", "Springfield", "12345", 40.71, -74.00));
        service.registerCustomer(Bob);


        Restaurant pizzaPalace = service.registerRestaurant("Pizza Palace", pizzaAddress);
        Restaurant burgerBarn = service.registerRestaurant("Burger Barn", burgerAddress);
        Restaurant tacoTown = service.registerRestaurant("Taco Town", tacoAddress);

        pizzaPalace.addToMenu(new MenuItem("P001", "Margherita Pizza", 12.99));
        pizzaPalace.addToMenu(new MenuItem("P002", "Veggie Pizza", 11.99));
        burgerBarn.addToMenu(new MenuItem("B001", "Classic Burger", 8.99));
        tacoTown.addToMenu(new MenuItem("T001", "Crunchy Taco", 3.50));

        MenuItem chosenItem = pizzaPalace.getMenu().getItem("P001"); // Margherita Pizza
        Order order = service.placeOrder(alice.getUserId(), pizzaPalace.getId(), List.of(new OrderItem(chosenItem, 1)));
        service.pickUpOrder(order.getId()); 
    }
}