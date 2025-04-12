package strategies; 

import models.GoldSeat;
import models.PlatinumSeat;
import models.SilverSeat;

public class SeatFactory {
    public static ISeat createSeat(String seatId) {
        char column = seatId.charAt(0);
        return switch (column) {
            case 'P' -> new PlatinumSeat(seatId, 500);
            case 'G' -> new GoldSeat(seatId, 300);
            case 'S' -> new SilverSeat(seatId, 200);
            default -> throw new IllegalArgumentException("Invalid seat ID: " + seatId);
        };

    }
}
