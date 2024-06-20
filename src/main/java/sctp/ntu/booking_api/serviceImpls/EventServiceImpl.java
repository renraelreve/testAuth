package sctp.ntu.booking_api.serviceImpls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sctp.ntu.booking_api.entities.Event;
import sctp.ntu.booking_api.entities.Showtime;
import sctp.ntu.booking_api.exceptions.EventNotFoundException;
import sctp.ntu.booking_api.repositories.EventRepository;
import sctp.ntu.booking_api.repositories.ShowtimeRepository;
import sctp.ntu.booking_api.services.EventService;

@Service
// @Transactional // https://stackoverflow.com/questions/32269192/spring-no-entitymanager-with-actual-transaction-available-for-current-thread
public class EventServiceImpl implements EventService {
    
    private EventRepository eventRepository;
    private ShowtimeRepository showtimeRepository;

    // @Autowired
    private EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event findByDescription(String description) {
        Event event = eventRepository.findByDescription(description);
        return event;
    };

    @Override
    public Event findEventByEid(Integer eid) {
        Event event = eventRepository.findEventByEid(eid);
        return event;
    }

    @Override
    public ArrayList<Event> getAllEvents() {
        List<Event> allEvents = eventRepository.findAll();
        return (ArrayList<Event>) allEvents;
    }

    @Override
    public Showtime addShowtimeToEvent(Event event, Showtime showtime, ShowtimeRepository showtimeRepository) {
        // retrieve the Event from the database
        // [Activity 1 - Refactor code]

        // add the Event to the Showtime
        showtime.setEvent(event);
        // save the Showtime to the database
        return showtimeRepository.save(showtime);
    }

    @Override
    public void deleteAll() {
        eventRepository.deleteAll();
    }  

    @Override
    public void deleteEventbyEid(Integer eid) {
        eventRepository.deleteEventByEid(eid);
    }  

}
