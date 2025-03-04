package Patterns.Factory.Class;

import Patterns.Factory.Enums.VehicleType;
import Patterns.Factory.Interface.Vehicle;


public class VehicleFactory  {
    public static Vehicle createVehicle(VehicleType vehicleType){
        switch(vehicleType){
            case FOUR:
                return new FourWheeler();
            case TWO: 
                return new TwoWheeler();
            default:
                return new TwoWheeler();
        }
    }
}
