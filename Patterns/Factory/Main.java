package Patterns.Factory;

import Patterns.Factory.Class.VehicleFactory;
import Patterns.Factory.Enums.VehicleType;
import Patterns.Factory.Interface.Vehicle;

public class Main {
    public static void main(String[] args) {
        // Calling the static createVehicle method
        Vehicle vehicle1 = VehicleFactory.createVehicle(VehicleType.FOUR);
        vehicle1.drive();  // Outputs: Driving a four-wheeler.

        Vehicle vehicle2 = VehicleFactory.createVehicle(VehicleType.TWO);
        vehicle2.drive();  // Outputs: Driving a two-wheeler.
    }
}


