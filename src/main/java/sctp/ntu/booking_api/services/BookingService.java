package sctp.ntu.booking_api.services;

import java.util.ArrayList;

import sctp.ntu.booking_api.entities.Booking;
import sctp.ntu.booking_api.entities.Showtime;
import sctp.ntu.booking_api.entities.User;

public interface BookingService {

  // // CREATE
  Booking createBooking(Showtime showtime, User user, Integer bookedSeats);

  // // READ GET ONE
  Booking findBookingByBid(Integer bid);

  // READ GET ALL
  ArrayList<Booking> getAllBookings();

  // UPDATE
  Booking updateBooking(Booking booking, int newBookedSeats);

  // // DELETE
  // void deleteBooking(Integer bid);

  Booking addBooking(int uid, int sid, Booking booking);

  // ArrayList<Booking> searchBooking(Integer bid);

}
