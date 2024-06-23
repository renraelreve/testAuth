package sctp.ntu.booking_api.serviceImpls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import sctp.ntu.booking_api.entities.Booking;
import sctp.ntu.booking_api.entities.User;
import sctp.ntu.booking_api.entities.Showtime;
import sctp.ntu.booking_api.exceptions.BookingNotFoundException;
import sctp.ntu.booking_api.exceptions.UserNotFoundException;
import sctp.ntu.booking_api.exceptions.ShowtimeNotFoundException;
import sctp.ntu.booking_api.repositories.BookingRepository;
import sctp.ntu.booking_api.repositories.UserRepository;
import sctp.ntu.booking_api.repositories.ShowtimeRepository;
import sctp.ntu.booking_api.services.BookingService;
import sctp.ntu.booking_api.services.ShowtimeService;

@Primary
@Service
public class BookingWithLoggingServiceImpl implements BookingService {

  private final Logger logger = LoggerFactory.getLogger(BookingWithLoggingServiceImpl.class);

  private ShowtimeService showtimeService;
  private BookingRepository bookingRepository;
  private UserRepository userRepository;
  private ShowtimeRepository showtimeRepository;

  // @Autowired
  public BookingWithLoggingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository,
      ShowtimeRepository showtimeRepository, ShowtimeService showtimeService) {
    this.showtimeService = showtimeService;
    this.bookingRepository = bookingRepository;
    this.userRepository = userRepository;
    this.showtimeRepository = showtimeRepository;
  }

  @Override
  public Booking createBooking(Showtime showtime, User user, Integer bookedSeats) {
    // initialise booking with 0
    Booking newBooking = new Booking(showtime, user, bookedSeats);
    bookingRepository.save(newBooking);
    // update showtime with new number of seats
    showtimeService.changeBalSeats(showtime, newBooking, 2 * bookedSeats);
    return newBooking;
  }

  @Override
  public Booking findBookingByBid(Integer bid) {
    Booking booking = bookingRepository.findBookingByBid(bid);
    return booking;
  };

  // @Override
  // public Booking getBooking(Integer bid) {
  // // Optional<Customer> optionalCustomer = customerRepository.findById(id);
  // // if(optionalCustomer.isPresent()) {
  // // Customer foundCustomer = optionalCustomer.get();
  // // return foundCustomer;
  // // }
  // // throw new CustomerNotFoundException(id);
  // return bookingRepository.findById(bid).orElseThrow(()-> new
  // BookingNotFoundException(id));
  // }

  @Override
  public ArrayList<Booking> getAllBookings() {
    List<Booking> allBookings = bookingRepository.findAll();
    return (ArrayList<Booking>) allBookings;
  }

  @Override
  public Booking updateBooking(int bid, Booking booking) {
    logger.info("BookingServiceWithLoggingImpl.updateBooking() called");
    logger.info("🟢 Testing my log file in BookingService");
    Booking bookingToUpdate = bookingRepository.findById(bid).orElseThrow(() -> new BookingNotFoundException(bid));

    bookingToUpdate.setBookedSeats(booking.getBookedSeats());
    Showtime showtime = bookingToUpdate.getShowtime();
    updateBalanceSeats(showtime);
    System.out.println("Booking " + bookingToUpdate.getBid() + " has been updated");
    return bookingRepository.save(bookingToUpdate);
  }

  @Override
  public void deleteBooking(Integer bid) {
    Booking bookingToDelete = bookingRepository.findById(bid).orElseThrow(() -> {

      return new BookingNotFoundException(bid);
    });

    // Get the showtime associated with the booking
    Showtime showtime = bookingToDelete.getShowtime();

    // Remove the booking from the showtime's bookings list
    showtime.getBooking().remove(bookingToDelete);
    bookingRepository.deleteById(bid);
    updateBalanceSeats(showtime);
  }

  @Override
  // @Transactional
  public Booking addBooking(int uid, int sid, Booking booking) {
    User user = userRepository.findById(uid).orElseThrow(() -> new UserNotFoundException(uid));
    Showtime showtime = showtimeRepository.findById(sid).orElseThrow(() -> new ShowtimeNotFoundException(sid));

    booking.setUser(user);
    booking.setShowtime(showtime);

    Booking savedBooking = bookingRepository.save(booking);
    updateBalanceSeats(showtime);
    return savedBooking;
  }

  private void updateBalanceSeats(Showtime showtime) {
    List<Booking> bookings = showtime.getBooking();
    int totalSeats = showtime.getTotalSeats();
    int bookedSeats = bookings.stream().mapToInt(Booking::getBookedSeats).sum();
    showtime.setBalSeats(totalSeats - bookedSeats);
    logger
        .info("🟢 The balance seats for " + showtime.getEvent().getDescription() + " on " + showtime.getDate()
            + " is now " + showtime.getBalSeats());
    showtimeRepository.save(showtime);
  }
}
