package Patterns.Decorator.Class;

import Patterns.Decorator.Interface.Pizza;

public abstract class PizzaDecorator implements Pizza {
    protected Pizza decoratedPizza;

    public PizzaDecorator(Pizza decoratedPizza){
        this.decoratedPizza=decoratedPizza;
    }
    @Override
    public String getDescription() {
        return decoratedPizza.getDescription();
    }

    @Override
    public double cost() {
        return decoratedPizza.cost();
    }
}
