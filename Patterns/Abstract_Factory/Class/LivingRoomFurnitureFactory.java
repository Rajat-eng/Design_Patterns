package Patterns.Abstract_Factory.Class;

import Patterns.Abstract_Factory.Interface.Chair;
import Patterns.Abstract_Factory.Interface.CoffeeTable;
import Patterns.Abstract_Factory.Interface.Desk;
import Patterns.Abstract_Factory.Interface.FurnitureFactory;
import Patterns.Abstract_Factory.Interface.Sofa;

public class LivingRoomFurnitureFactory implements FurnitureFactory {
    public Sofa createSofa() {
        return new LivingRoomSofa();
    }


    public CoffeeTable createCoffeeTable() {
        return new LivingRoomCoffeeTable();
    }


    public Desk createDesk() {
        // Living Room doesn't need a desk, so returning null or default could be an option.
        return null; 
    }


    public Chair createChair() {
        // Living Room doesn't need an office chair, but it could be a regular chair.
        return null;
    }
}
