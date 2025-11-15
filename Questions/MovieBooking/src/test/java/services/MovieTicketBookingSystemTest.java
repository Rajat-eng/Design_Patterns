package services;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import models.Movie;
import models.Show;
import models.Theater;
import models.User;

public class MovieTicketBookingSystemTest {
    @Test
    public void testBookSeatsAndRevenueCalculation() {
        MovieTicketBookingSystem system = new MovieTicketBookingSystem();
        User user = new User("test@example.com");

        Theater theater = new Theater(1, "PVR", "Delhi");
        system.addTheather(theater);

        Movie movie = new Movie("Test Movie", "Director", 120, "Hindi", "U");
        system.addMovie(movie);

        Show show = new Show(1);
        show.setMovieAndTheaterForTheShow(movie, theater);
        List<String> seats = List.of("A1", "A2", "A3");
        system.addShowAndSeats(show, seats);

        assertTrue(system.areSeatsAvailable(1, List.of("A1", "A2")));
        double price = system.bookSeatsAndGetPrice(user, 1, List.of("A1", "A2"));

        assertTrue(price > 0);

        
        system.calclateTotalRevenueForAllTheaters();
    }

}
