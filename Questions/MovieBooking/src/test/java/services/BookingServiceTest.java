package services;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.Booking;
import models.Movie;
import models.Show;
import models.Theater;
import models.User;
import models.enums.SeatStatus;

public class BookingServiceTest {
    private ShowService showService;
    private BookingService bookingService;
    private User user;
    private Show show;
    private Movie movie;
    private Theater theater;

    @BeforeEach
    public void setUp() {
        show = new Show(1);
        showService = new ShowService();
        bookingService = new BookingService(showService);
        user = new User("rv@gmail.com");
        movie= new Movie("Movie1", "Action", 120, "English", "D");
        theater = new Theater(1, "t1", "Delhi");
        showService.addShow(show.getShowID(), show);
        showService.addSeatsToShow(List.of("A1", "A2", "A3"), show.getShowID());
        showService.getShow(show.getShowID()).setMovieAndTheaterForTheShow(movie, theater);
    }

    @Test
    public void test_BookSeatsAndGetPrice_With_ValidSeat_And_ValidShowId() {
        List<String> seatIds = List.of("A1", "A2");
        double price = bookingService.bookSeatsAndGetPrice(user, show.getShowID(), seatIds);
        assertTrue(price > 0);
        assertEquals(SeatStatus.BOOKED, show.getSeatBySeatId("A1").getSeatStatus());
        assertEquals(SeatStatus.BOOKED, show.getSeatBySeatId("A2").getSeatStatus());
    }

    @Test
    public void test_BookSeatsAndGetPrice_With_InValidSeat_And_ValidShowId() {
        List<String> seatIds = List.of("A1", "A2", "A4");
        double price = bookingService.bookSeatsAndGetPrice(user, show.getShowID(), seatIds);
        assertEquals(price, 0);
        assertEquals(SeatStatus.AVAILABLE, show.getSeatBySeatId("A1").getSeatStatus());
        assertEquals(SeatStatus.AVAILABLE, show.getSeatBySeatId("A2").getSeatStatus());
        assertEquals(null, show.getSeatBySeatId("A4"));
    }

    @Test
    public void test_getTotalBookingWithTheaterId_when_ValidShowId_ValidSeatId() {
        List<String> seatIds = List.of("A1", "A2");
        bookingService.bookSeatsAndGetPrice(user, show.getShowID(), seatIds);
        List<Booking> bookings = bookingService.getAllBookingsByTheaterId(1);
        assertEquals(1, bookings.size());
    }

    @Test
    public void test_getTotalBookingWithTheaterId_when_InValidTheaterId(){
        List<String> seatIds = List.of("A1", "A2", "A4");
        bookingService.bookSeatsAndGetPrice(user, show.getShowID(), seatIds);
        List<Booking> bookings = bookingService.getAllBookingsByTheaterId(2);
        assertEquals(0, bookings.size());
    }
}
