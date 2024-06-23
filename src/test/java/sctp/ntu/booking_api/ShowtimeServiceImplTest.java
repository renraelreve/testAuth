package sctp.ntu.booking_api;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import sctp.ntu.booking_api.entities.Booking;
import sctp.ntu.booking_api.entities.Showtime;
import sctp.ntu.booking_api.repositories.ShowtimeRepository;
import sctp.ntu.booking_api.serviceImpls.ShowtimeServiceImpl;

public class ShowtimeServiceImplTest {

    @Mock
    private ShowtimeRepository showtimeRepository;

    @InjectMocks
    private ShowtimeServiceImpl showtimeServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindShowtimeBySid_Valid() {
        Showtime showtime = new Showtime();
        when(showtimeRepository.findShowtimeBySid(anyInt())).thenReturn(showtime);
        Showtime foundShowtime = showtimeServiceImpl.findShowtimeBySid(1);
        assertNotNull(foundShowtime);
    }

    @Test
    public void testChangeBalSeats_Valid() {
        Showtime showtime = new Showtime();
        Booking booking = new Booking();
        booking.setBookedSeats(2);
        showtime.setBalSeats(10);
        when(showtimeRepository.save(any(Showtime.class))).thenReturn(showtime);
        Showtime updatedShowtime = showtimeServiceImpl.changeBalSeats(showtime, booking, 4);
        assertEquals(8, updatedShowtime.getBalSeats());
    }
}