package Questions.Parking_Lot;

import java.util.ArrayList;
import java.util.List;

import Questions.Parking_Lot.Spot.ParkingSpot;
import Questions.Parking_Lot.Vehicles.Vehicle;
import Questions.Parking_Lot.Vehicles.VehicleType;

public class Level {
      private final int floor;
      private final List<ParkingSpot> parkingSpots;

      public Level(int floor, int numSpots) {
        this.floor = floor;
        parkingSpots = new ArrayList<>(numSpots);
        // Assign spots in ration of 50:40:10 for bikes, cars and trucks
        double spotsForBikes = 0.40;
        double spotsForCars = 0.40;
        double truckSpots= 0.10;

        int numBikes = (int) (numSpots * spotsForBikes);
        int numCars = (int) (numSpots * spotsForCars);
        int trucks = (int) (numSpots * truckSpots);

        for (int i = 1; i <= numBikes; i++) {
            parkingSpots.add(new ParkingSpot(i,VehicleType.MOTORCYCLE));
        }
        for (int i = numBikes + 1; i <= numBikes + numCars; i++) {
            parkingSpots.add(new ParkingSpot(i,VehicleType.CAR));
        }
        for (int i = numBikes + numCars + 1; i <= numBikes + numCars + trucks; i++) {
            parkingSpots.add(new ParkingSpot(i,VehicleType.TRUCK));
        }
        for (int i = numBikes + numCars + trucks + 1; i <=numSpots; i++) {
            parkingSpots.add(new ParkingSpot(i,VehicleType.ELECTRIC));
        }
    }

    public synchronized boolean parkVehicle(Vehicle vehicle) {
        for (ParkingSpot spot : parkingSpots) {
            if (spot.isAvailable() && spot.getVehicleType() == vehicle.getType()) {
                spot.parkVehicle(vehicle);
                return true;
            }
        }
        return false;
    }

    public synchronized boolean unparkVehicle(Vehicle vehicle) {
        for (ParkingSpot spot : parkingSpots) {
            if (!spot.isAvailable() && spot.getParkedVehicle().equals(vehicle)) {
                spot.unparkVehicle();
                return true;
            }
        }
        return false;
    }

    public void displayAvailability() {
        System.out.println("Level " + floor + " Availability:");
        for (ParkingSpot spot : parkingSpots) {
            System.out.println("Spot " + spot.getSpotNumber() + ": " + (spot.isAvailable() ? "Available For"  : "Occupied By ")+" "+spot.getVehicleType());
        }
    }
}
