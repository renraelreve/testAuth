package sctp.ntu.booking_api.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sctp.ntu.booking_api.entities.Booking;
import sctp.ntu.booking_api.entities.User;
import sctp.ntu.booking_api.entities.Event;
import sctp.ntu.booking_api.exceptions.BookingNotFoundException;
import sctp.ntu.booking_api.services.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {
    
    private BookingService bookingService;

    // @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Nested route - add event id and user to booking
    // CREATE
    // [Activity 2 - validation]
    @PostMapping("")
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Integer eid, @Valid @RequestBody Integer uid, @Valid @RequestBody Integer bookedSeats) {
        Booking newBooking = bookingService.createBooking(booking, eid, user, bookedSeats);
        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
    }

    // READ (GET ALL)
    @GetMapping("")
    public ResponseEntity<ArrayList<Booking>> getAllBookings() {
        ArrayList<Booking> allBookings = BookingService.getAllBookings();
        return new ResponseEntity<>(allBookings, HttpStatus.OK);
    }

    // READ (GET ONE)
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Integer bid) {
        try {
            Booking foundBooking = BookingService.getBooking(bid);
            return new ResponseEntity<>(foundBooking, HttpStatus.OK);
        } catch (BookingNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Integer bid, @RequestBody Booking booking, @RequestBody Integer bookedSeats) {
        try {
            Booking updatedBooking = BookingService.updateBooking(bid, booking);
            return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
        } catch (BookingNotFoundException e) {
            // not found, return 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }                
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Booking> deleteBooking(@PathVariable Integer bid) {
        try {
            BookingService.deleteBooking(bid);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (BookingNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
