package Questions.Parking_Lot.Spot;

import Questions.Parking_Lot.Level;
import Questions.Parking_Lot.Vehicles.*;

public class ParkingSpot {
    private final int spotNumber;
    private final VehicleType vehicleType;
    private final Level level;
    private Vehicle parkedVehicle;

    public ParkingSpot(int spotNumber,VehicleType vehicleType,Level level){
        this.spotNumber=spotNumber;
        this.vehicleType=vehicleType;
        this.level=level;
    }
    public synchronized boolean isAvailable() {
        return parkedVehicle == null;
    }

    public synchronized void parkVehicle(Vehicle vehicle) {
        if (isAvailable() && vehicle.getType() == vehicleType) {
            parkedVehicle = vehicle;
        } else {
            throw new IllegalArgumentException("Invalid vehicle type or spot already occupied.");
        }
    }

    public synchronized void unparkVehicle() {
        parkedVehicle = null;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public Level getLevel(){
        return this.level;
    }
}
