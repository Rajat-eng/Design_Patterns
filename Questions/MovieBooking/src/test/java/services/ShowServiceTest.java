package services;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.Show;
import models.enums.SeatStatus;


public class ShowServiceTest {
    private ShowService showService;
    private Show show;

    @BeforeEach
    public void setUp() {
        showService = new ShowService();
        show = new Show(1);
        showService.addShow(show.getShowID(), show);
        showService.getShow(show.getShowID()).addSeatsToShow(List.of("A1", "A2", "A3"));
    }

    @Test
    public void testAddShow() {
        showService.addShow(1, show);
        Show retrievedShow = showService.getShow(1);
        assert retrievedShow != null : "Show should be added successfully";
        assert retrievedShow.getShowID() == 1 : "Show ID should match the added show ID";
    }

    @Test
    public void testIsValidShow() {
        showService.addShow(1, show);
        boolean isValid = showService.isValidShow(1);
        assertEquals(isValid, true); 
    }

    @Test
    public void testAddSeatsToShow() {
        showService.addShow(1, show);
        List<String> seatIds = List.of("A1", "A2", "A3");
        showService.addSeatsToShow(seatIds, 1);
        assertEquals(show.getAllSeats().size(), 3, "Seats should be added to the show successfully");
    }


    @Test 
    public void areSeatsAvailable(){
       showService.addShow(1, show);
       List<String> seatIds = List.of("A1", "A2", "A3");

       boolean areSeatsAvailable = showService.areSeatsAvailable(1, seatIds);
       assertEquals(areSeatsAvailable, true);

    }

    @Test
    public void testAreSeatsAvailable_withInvalidSeat() {
        List<String> seatIds = Arrays.asList("X1");
        showService.addSeatsToShow(Arrays.asList("A1", "A2"), 1);
        boolean available = showService.areSeatsAvailable(1, seatIds);
        assertFalse(available);
    }


    @Test
    public void test_BookSeats_getTotalPrice(){
        showService.addShow(1, show);
        List<String> seatIds = Arrays.asList("A1", "A2", "A3");
        showService.addSeatsToShow(seatIds, 1);
        int totalPrice = showService.bookSeats(1, seatIds);
        assert(totalPrice > 0);
        assertEquals(SeatStatus.BOOKED, show.getSeatBySeatId("A1").getSeatStatus());
    }

    @Test
    public void test_BookSeats_getTotalPrice_WithInvalidShowId(){
        List<String> seatIds = Arrays.asList("A1", "A2", "A3");
        int totalPrice = showService.bookSeats(2, seatIds);
        assertEquals(totalPrice, 0);
        assertEquals(SeatStatus.AVAILABLE, show.getSeatBySeatId("A1").getSeatStatus());
    }

}
