package Patterns.Decorator.Class;

import Patterns.Decorator.Interface.Pizza;

public class PepperoniDecorator extends PizzaDecorator {
    public PepperoniDecorator(Pizza decoratedPizza){
        super(decoratedPizza);
    }
    @Override
    public String getDescription() {
        return decoratedPizza.getDescription() + ",Pepperoni";
    }

    @Override
    public double cost() {
        return decoratedPizza.cost() + 15;
    }
}
