package sctp.ntu.booking_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sctp.ntu.booking_api.entities.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Booking findBookingByBid(Integer bid);
}
