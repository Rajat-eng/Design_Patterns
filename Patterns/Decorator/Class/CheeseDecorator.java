package Patterns.Decorator.Class;

import Patterns.Decorator.Interface.Pizza;

public class CheeseDecorator extends PizzaDecorator {
    public CheeseDecorator(Pizza decoratedPizza){
        super(decoratedPizza);
    }
    @Override
    public String getDescription() {
        return decoratedPizza.getDescription() + ",Cheese";
    }

    @Override
    public double cost() {
        return decoratedPizza.cost() + 10;
    }
}
