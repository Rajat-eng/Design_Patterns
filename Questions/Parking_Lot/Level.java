package Questions.Parking_Lot;

import Questions.Parking_Lot.Spot.ParkingSpot;
import Questions.Parking_Lot.Vehicles.Vehicle;
import Questions.Parking_Lot.Vehicles.VehicleType;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private final int floor;
    private final List<ParkingSpot> parkingSpots;

    public Level(int floor, int numSpots) {
        this.floor = floor;
        parkingSpots = new ArrayList<>(numSpots);
        // Assign spots in ration of 50:40:10 for bikes, cars and trucks
        double spotsForBikes = 0.40;
        double spotsForCars = 0.40;
        double truckSpots = 0.10;

        int numBikes = (int) (numSpots * spotsForBikes);
        int numCars = (int) (numSpots * spotsForCars);
        int trucks = (int) (numSpots * truckSpots);
        int numElectric = numSpots - (numBikes + numCars + trucks);

        int spotId = 1;
        for (int i = 0; i < numBikes; i++)
            parkingSpots.add(new ParkingSpot(spotId++, VehicleType.MOTORCYCLE, this));
        for (int i = 0; i < numCars; i++)
            parkingSpots.add(new ParkingSpot(spotId++, VehicleType.CAR, this));
        for (int i = 0; i < trucks; i++)
            parkingSpots.add(new ParkingSpot(spotId++, VehicleType.TRUCK, this));
        for (int i = 0; i < numElectric; i++)
            parkingSpots.add(new ParkingSpot(spotId++, VehicleType.ELECTRIC, this));
    }

    public synchronized ParkingSpot parkVehicle(Vehicle vehicle) {
        for (ParkingSpot spot : parkingSpots) {
            if (spot.isAvailable() && spot.getVehicleType() == vehicle.getType()) {
                spot.parkVehicle(vehicle);
                return spot;
            }
        }
        return null;
    }

    public synchronized boolean unparkVehicle(Vehicle vehicle) {
        for(ParkingSpot parkingSpot: this.parkingSpots){
            if(!parkingSpot.isAvailable() && parkingSpot.getParkedVehicle().equals(vehicle)){
                parkingSpot.unparkVehicle();
                return true;
            }
        }
        return false;
    }

    public void displayAvailability() {
        System.out.println("Level " + floor + " Availability:");
        for (ParkingSpot spot : parkingSpots) {
            System.out.println("Spot " + spot.getSpotNumber() + ": "
                    + (spot.isAvailable() ? "Available For" : "Occupied By ") + " " + spot.getVehicleType());
        }
    }
}
