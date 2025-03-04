package Patterns.Decorator.Class;

import Patterns.Decorator.Interface.Pizza;

public class MushRoomDecorator extends PizzaDecorator{
    public MushRoomDecorator(Pizza decoratedPizza){
        super(decoratedPizza);
    }
    @Override
    public String getDescription() {
        return decoratedPizza.getDescription() + ", Mushrooms";
    }

    @Override
    public double cost() {
        return decoratedPizza.cost() + 20;
    }
}
