package Patterns.Builder.Class.Dishes;

import Patterns.Builder.Interface.MainCourse;

public class Pasta implements MainCourse {
    @Override
    public void serve() {
        System.out.println("Serving Pasta as the main course.");
    }
}
