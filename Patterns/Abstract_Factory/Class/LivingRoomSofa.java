package Patterns.Abstract_Factory.Class;


import Patterns.Abstract_Factory.Interface.Sofa;

public class LivingRoomSofa implements Sofa{
    public void sitOn() {
        System.out.println("Sitting on a comfy Living Room Sofa.");
    }
    public void placeItems() {
        System.out.println("Placing items on a Living Room Coffee Table.");
    }
}
