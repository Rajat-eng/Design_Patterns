package Patterns.Adapter.Class;

import Patterns.Adapter.Interface.Charger;

public class Iphone4sCharger implements Charger {
    public void charge(){
        System.err.println("charge");
    }
    
}