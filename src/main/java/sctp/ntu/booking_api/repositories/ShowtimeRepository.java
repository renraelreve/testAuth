package sctp.ntu.booking_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sctp.ntu.booking_api.entities.Showtime;

public interface ShowtimeRepository extends JpaRepository<Showtime, Integer> {
        
        Showtime findShowtimeBySid(Integer sid);

}
