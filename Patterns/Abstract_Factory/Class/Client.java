package Patterns.Abstract_Factory.Class;

import Patterns.Abstract_Factory.Interface.Chair;
import Patterns.Abstract_Factory.Interface.CoffeeTable;
import Patterns.Abstract_Factory.Interface.Desk;
import Patterns.Abstract_Factory.Interface.FurnitureFactory;
import Patterns.Abstract_Factory.Interface.Sofa;

public class Client {
     private Sofa sofa;
    private CoffeeTable coffeeTable;
    private Desk desk;
    private Chair chair;

    public Client(FurnitureFactory furnitureFactory) {
        sofa = furnitureFactory.createSofa();
        coffeeTable = furnitureFactory.createCoffeeTable();
        desk = furnitureFactory.createDesk();
        chair = furnitureFactory.createChair();
    }

    public void displayFurniture() {
        if (sofa != null) sofa.sitOn();
        if (coffeeTable != null) coffeeTable.placeItems();
        if (desk != null) desk.workOn();
        if (chair != null) chair.sitOn();
    }
}
