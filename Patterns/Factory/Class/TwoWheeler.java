package Patterns.Factory.Class;

import Patterns.Factory.Interface.Vehicle;

public class TwoWheeler implements Vehicle {
    public void drive(){
        System.err.println("two wheeler");
    }
}
