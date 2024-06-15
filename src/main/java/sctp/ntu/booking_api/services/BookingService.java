package sctp.ntu.booking_api.services;

import java.util.ArrayList;

import sctp.ntu.booking_api.entities.Booking;

public interface BookingService {
    
    // CREATE
    Booking createBooking(Integer eid, Integer uid, Integer bookedSeats);

    // READ GET ONE
    Booking getBooking(Integer bid);

    // READ GET ALL
    ArrayList<Booking> getAllBookings();

    // UPDATE
    Booking updateBooking(Integer bid, Integer bookedSeats);

    // DELETE
    void deleteBooking(Integer bid);
    
}
