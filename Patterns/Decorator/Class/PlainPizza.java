package Patterns.Decorator.Class;

import Patterns.Decorator.Interface.Pizza;

public class PlainPizza implements Pizza{
    @Override
    public String getDescription() {
        return "Plain pizza";
    }

    @Override
    public double cost() {
        return 10; // Base price of the pizza
    }
}
