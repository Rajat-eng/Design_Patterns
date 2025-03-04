package Patterns.Decorator;

import Patterns.Decorator.Class.CheeseDecorator;
import Patterns.Decorator.Class.PepperoniDecorator;
import Patterns.Decorator.Class.PlainPizza;
import Patterns.Decorator.Interface.Pizza;

public class Main {
    public static void main(String[] args) {
        Pizza pizza=new PlainPizza();
        pizza=new CheeseDecorator(new PepperoniDecorator(pizza));
        System.out.println(pizza.getDescription() + " costs" +  pizza.cost());
    }
}
