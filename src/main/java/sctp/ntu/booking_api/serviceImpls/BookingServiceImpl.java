package sctp.ntu.booking_api.serviceImpls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

@Service
public class BookingServiceImpl implements BookingService {

  private ShowtimeService showtimeService;

  private BookingRepository bookingRepository;
  private UserRepository userRepository;
  private ShowtimeRepository showtimeRepository;

  // @Autowired
  public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository,
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
  // public Interaction addInteractionToCustomer(Long id, Interaction interaction)
  // {
  // // retrieve the customer from the database
  // // [Activity 1 - Refactor code]
  // Customer selectedCustomer = customerRepository.findById(id).orElseThrow(() ->
  // new CustomerNotFoundException(id));

  // // add the customer to the interaction
  // interaction.setCustomer(selectedCustomer);
  // // save the interaction to the database
  // return interactionRepository.save(interaction);

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
  public Booking updateBooking(Booking bookingToUpdate, int newBookedSeats) {
    // retrieve the customer from the database
    // [Activity 1 - Refactor code]

    // update the balance seats in the showtime slot due to new number of booked
    // seats
    showtimeService.changeBalSeats(bookingToUpdate.getShowtime(), bookingToUpdate, newBookedSeats);
    // update the booking retrieved from the parameter
    bookingToUpdate.setBookedSeats(newBookedSeats);
    // save the updated customer back to the database
    return bookingRepository.save(bookingToUpdate);
  }

  // @Override
  // public void deleteBooking(Integer bid) {
  // bookingRepository.deleteById(bid);
  // }

  // }

}
