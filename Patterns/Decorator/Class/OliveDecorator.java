package Patterns.Decorator.Class;

import Patterns.Decorator.Interface.Pizza;

public class OliveDecorator extends PizzaDecorator {
    public OliveDecorator(Pizza decoratedPizza){
        super(decoratedPizza);
    }
    @Override
    public String getDescription() {
        return decoratedPizza.getDescription() + ",Olives";
    }

    @Override
    public double cost() {
        return decoratedPizza.cost() + 25;
    }
}
