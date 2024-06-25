package sctp.ntu.booking_api.exceptions;

public class SeatsNotEnoughException extends Exception {

  public SeatsNotEnoughException(int bookedSeats) {
    super(bookedSeats + " seats booked. ");
  }
}
