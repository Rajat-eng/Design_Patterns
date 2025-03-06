package Questions.Parking_Lot.Vehicles;

public class Electric extends Vehicle {
    public Electric(String license){
        super(license,VehicleType.ELECTRIC);
        this.isElectric=true;
    }
}
