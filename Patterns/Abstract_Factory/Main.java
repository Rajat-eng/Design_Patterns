package Patterns.Abstract_Factory;

import Patterns.Abstract_Factory.Class.Client;
import Patterns.Abstract_Factory.Class.LivingRoomFurnitureFactory;
import Patterns.Abstract_Factory.Class.OfficeRoomFurnitureFactory;
import Patterns.Abstract_Factory.Interface.FurnitureFactory;

public class Main {
    public static void main(String[] args) {
        // Create Living Room Furniture using Living Room Factory
        FurnitureFactory livingRoomFactory = new LivingRoomFurnitureFactory();
        Client livingRoomClient = new Client(livingRoomFactory);
        livingRoomClient.displayFurniture();  // Outputs furniture related to living room.

        // Create Office Furniture using Office Factory
        FurnitureFactory officeFactory = new OfficeRoomFurnitureFactory();
        Client officeClient = new Client(officeFactory);
        officeClient.displayFurniture();  // Outputs furniture related to office room.
    }
}
