package strategies;

import models.enums.SeatStatus;
import models.enums.SeatType;

public interface ISeat {
    public String getSeatId();
    public int getPrice();
    public SeatType getSeatType();
    public void setSeatStatus(SeatStatus seatStatus);
    public SeatStatus getSeatStatus();
    public boolean isSeatBooked();
}
