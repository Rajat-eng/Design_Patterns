package Patterns.Abstract_Factory.Class;

import Patterns.Abstract_Factory.Interface.Chair;
import Patterns.Abstract_Factory.Interface.CoffeeTable;
import Patterns.Abstract_Factory.Interface.Desk;
import Patterns.Abstract_Factory.Interface.FurnitureFactory;
import Patterns.Abstract_Factory.Interface.Sofa;

public class OfficeRoomFurnitureFactory implements FurnitureFactory{
    @Override
    public Sofa createSofa() {
        // Office doesn't need a sofa, returning null or default could be an option.
        return null; 
    }

    @Override
    public CoffeeTable createCoffeeTable() {
        // Office doesn't need a coffee table, so returning null or default.
        return null;
    }

    @Override
    public Desk createDesk() {
        return new OfficeDesk();
    }

    @Override
    public Chair createChair() {
        return new OfficeChair();
    }
}
