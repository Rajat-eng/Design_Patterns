package Patterns.Builder.Class.Dishes;

import Patterns.Builder.Interface.SideDish;

public class Salad implements SideDish {
    @Override
    public void serve() {
        System.out.println("Serving Salad as a side dish.");
    }
}
