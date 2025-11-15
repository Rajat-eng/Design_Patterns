package strategies; 

import models.GoldSeat;
import models.PlatinumSeat;
import models.SilverSeat;

public class SeatFactory {
    public static ISeat createSeat(String seatId) {
        char column = seatId.charAt(0);
        return switch (column) {
            case 'C' -> new PlatinumSeat(seatId, 320);
            case 'B' -> new GoldSeat(seatId, 280);
            case 'A' -> new SilverSeat(seatId, 240);
            default -> throw new IllegalArgumentException("Invalid seat ID: " + seatId);
        };

    }
}
