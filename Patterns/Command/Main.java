package Patterns.Command;

import Patterns.Command.Class.CancelOrderCommand;
import Patterns.Command.Class.Chef;
import Patterns.Command.Class.PlaceOrderCommand;
import Patterns.Command.Class.Waiter;
import Patterns.Command.Interface.OrderCommand;

public class Main {
    public static void main(String[] args) {
        Chef chef = new Chef();
        OrderCommand pizzaOrder=new PlaceOrderCommand(chef, "Pizza");
        OrderCommand pastaOrder=new PlaceOrderCommand(chef, "Pasta");
        Waiter waiter = new Waiter();
        waiter.takeOrder(pizzaOrder);
        waiter.takeOrder(pastaOrder);
        OrderCommand cancelPizzaOrder= new CancelOrderCommand(chef, "Pizza");
        waiter.takeOrder(cancelPizzaOrder);
    }
}
