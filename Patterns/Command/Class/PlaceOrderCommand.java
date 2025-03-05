package Patterns.Command.Class;

import Patterns.Command.Interface.OrderCommand;

public class PlaceOrderCommand implements OrderCommand {
    private Chef chef;
    private String dish;
    public PlaceOrderCommand(Chef chef, String dish) {
        this.chef = chef;
        this.dish = dish;
    }
    @Override
    public void execute() {
        chef.prepareDish(dish);
    }

    @Override
    public void undo() {
        chef.cancelDish(dish);
    }
}
