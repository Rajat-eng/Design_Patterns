package Patterns.Builder.Class.Dishes;

import Patterns.Builder.Interface.MainCourse;

public class Burger implements MainCourse{
    @Override
    public void serve() {
        System.out.println("Serving Burger as the main course.");
    }
}
