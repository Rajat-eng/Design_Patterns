package Questions.Parking_Lot.Vehicles;

public abstract class Vehicle {
    protected String licensePlate;
    protected VehicleType type;
    protected boolean isElectric;
    
    public Vehicle(String licensePlate, VehicleType type) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.isElectric=false;
    }

    public VehicleType getType() {
        return type;
    }


}
