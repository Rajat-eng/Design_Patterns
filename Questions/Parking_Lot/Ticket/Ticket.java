package Questions.Parking_Lot.Ticket;

import Questions.Parking_Lot.Spot.ParkingSpot;


public class Ticket{
    private ParkingSpot parkingSpot;
    private String ticketId;
    private long timeStarted;
    private long timeEnded;
    public Ticket(String ticketId){
        this.ticketId=ticketId;
        
    }

    public void assignSpotOnEntry(ParkingSpot parkingSpot){
        this.parkingSpot=parkingSpot;
        this.timeStarted=System.currentTimeMillis();
    }

    public void leaveSpotOnExit(ParkingSpot parkingSpot){
        this.parkingSpot=null;
        this.timeStarted=System.currentTimeMillis();
    }

}