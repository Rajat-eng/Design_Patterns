package Questions.Parking_Lot;

import Questions.Parking_Lot.Spot.ParkingSpot;
import Questions.Parking_Lot.Vehicles.Vehicle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {
    private static ParkingLot instance;
    private final List<Level> levels;
    private Map<Vehicle,ParkingSpot> vehicleSpotMap;

    private ParkingLot() {
        levels = new ArrayList<>();
        this.vehicleSpotMap=new HashMap<>();
    }

    public ParkingSpot getParkedVehicleSpot(Vehicle vehicle){
        return vehicleSpotMap.get(vehicle);
    }
    public ParkingSpot assignSpotToVehicle(Vehicle vehicle,ParkingSpot parkingSpot){
        return vehicleSpotMap.put(vehicle,parkingSpot);
    }
    public static synchronized ParkingLot getInstance() {
        if (instance == null) {
            instance = new ParkingLot();
        }
        return instance;
    }

    public void addLevel(Level level) {
        levels.add(level);
    }

    public boolean parkVehicle(Vehicle vehicle) {
        for (Level level : levels) {
            ParkingSpot parkingSpot=level.parkVehicle(vehicle); 
            assignSpotToVehicle(vehicle, parkingSpot);
            return true;   
        }
        System.out.println("Could not park vehicle.");
        return false;
    }

    public boolean unparkVehicle(Vehicle vehicle) {
        ParkingSpot parkedSpot= vehicleSpotMap.get(vehicle);
        if(parkedSpot==null){
            System.out.println("No assigned Spot for this vehicle");
            return false; 
        }
        Level level = parkedSpot.getLevel(); 
        level.unparkVehicle(vehicle);
        return true; 
    }

    public void displayAvailability() {
        for (Level level : levels) {
            level.displayAvailability();
        }
    }
}

