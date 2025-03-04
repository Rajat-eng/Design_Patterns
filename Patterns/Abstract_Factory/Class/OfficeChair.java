package Patterns.Abstract_Factory.Class;

import Patterns.Abstract_Factory.Interface.Chair;

public class OfficeChair implements Chair {
    public void sitOn() {
        System.out.println("Sitting on an ergonomic Office Chair.");
    }
}
