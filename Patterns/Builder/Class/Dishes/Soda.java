package Patterns.Builder.Class.Dishes;

import Patterns.Builder.Interface.Drink;

public class Soda implements Drink {
    @Override
    public void serve() {
        System.out.println("Serving Soda as the drink.");
    }
}
