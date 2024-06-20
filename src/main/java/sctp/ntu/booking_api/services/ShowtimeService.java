package sctp.ntu.booking_api.services;

import sctp.ntu.booking_api.entities.Booking;
import sctp.ntu.booking_api.entities.Showtime;

public interface ShowtimeService {
    
    Showtime findShowtimeBySid(Integer sid);

    Showtime changeBalSeats(Showtime showtime, Booking booking, int newBookedSeats);

}
