package sctp.ntu.booking_api.services;

import java.util.ArrayList;

import sctp.ntu.booking_api.entities.Event;
import sctp.ntu.booking_api.entities.Showtime;
import sctp.ntu.booking_api.repositories.ShowtimeRepository;

public interface EventService {

    Event findByDescription(String description);

    Event findEventByEid(Integer eid);

    ArrayList<Event> getAllEvents();

    Showtime addShowtimeToEvent(Event event, Showtime showtime, ShowtimeRepository showtimeRepository);

    void deleteAll();

    void deleteEventbyEid(Integer eid);
}
