package Patterns.Command.Class;

public class Chef {
    public void prepareDish(String dish) {
        System.out.println("Chef is preparing: " + dish);
    }

    public void cancelDish(String dish) {
        System.out.println("Chef has canceled: " + dish);
    }
}
