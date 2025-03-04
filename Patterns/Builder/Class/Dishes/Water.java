package Patterns.Builder.Class.Dishes;

import Patterns.Builder.Interface.Drink;

public class Water implements Drink {
    @Override
    public void serve() {
        System.out.println("Serving Water as the drink.");
    }
}
