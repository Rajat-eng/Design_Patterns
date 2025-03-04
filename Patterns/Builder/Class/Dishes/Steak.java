package Patterns.Builder.Class.Dishes;

import Patterns.Builder.Interface.MainCourse;

public class Steak implements MainCourse{
    @Override
    public void serve() {
        System.out.println("Serving Salad as a side dish.");
    }
}
