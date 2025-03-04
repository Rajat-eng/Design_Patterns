package Patterns.Adapter.Class;

import Patterns.Adapter.Interface.Charger;

public class Iphone4sto6sCharger implements Charger {
    Iphone4sCharger iphone4sCharger;
    // charge 6s from 4s charger
    // 4s is adaptee which needs to be adapted for 6s charger
    public Iphone4sto6sCharger(){
        this.iphone4sCharger = new Iphone4sCharger();
    }
    @Override
    public void charge()
    {
        iphone4sCharger.charge();
    }
}
