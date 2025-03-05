package Patterns.Command.Class;

import Patterns.Command.Interface.OrderCommand;

public class CancelOrderCommand implements OrderCommand{
    private Chef chef;
    private String dish;
    public CancelOrderCommand(Chef chef, String dish) {
        this.chef = chef;
        this.dish = dish;
    }
    @Override
    public void execute() {
        chef.cancelDish(dish);
    }

    @Override
    public void undo() {
        chef.prepareDish(dish);
    }
}
