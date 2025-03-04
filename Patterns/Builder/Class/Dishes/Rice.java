package Patterns.Builder.Class.Dishes;

import Patterns.Builder.Interface.SideDish;

public class Rice implements SideDish {
    @Override
    public void serve() {
        System.out.println("Serving Rice as a side dish.");
    }
}
