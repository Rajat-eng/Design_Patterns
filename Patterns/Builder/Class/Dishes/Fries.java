package Patterns.Builder.Class.Dishes;

import Patterns.Builder.Interface.SideDish;

public class Fries implements SideDish {
    @Override
    public void serve() {
        System.out.println("Serving Fries as a side dish.");
    }
}
