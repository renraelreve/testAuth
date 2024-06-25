package sctp.ntu.booking_api;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import sctp.ntu.booking_api.entities.Event;
import sctp.ntu.booking_api.entities.Showtime;
import sctp.ntu.booking_api.repositories.EventRepository;
import sctp.ntu.booking_api.repositories.ShowtimeRepository;
import sctp.ntu.booking_api.serviceImpls.EventServiceImpl;

public class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ShowtimeRepository showtimeRepository;

    @InjectMocks
    private EventServiceImpl eventServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByDescription_Valid() {
        //SETUP
        Event event = new Event();
        //MOCK
        when(eventRepository.findByDescription(anyString())).thenReturn(event);
        //EXECUTE
        Event foundEvent = eventServiceImpl.findByDescription("Concert");
        //MOCK
        assertNotNull(foundEvent);
    }

    @Test
    public void testFindEventByEid_Valid() {
        //SETUP
        Event event = new Event();
        //MOCK
        when(eventRepository.findEventByEid(anyInt())).thenReturn(event);
        //EXECUTE
        Event foundEvent = eventServiceImpl.findEventByEid(1);
        //ASSERT
        assertNotNull(foundEvent);
    }

    @Test
    public void testGetAllEvents() {
        //SETUP
        List<Event> events = new ArrayList<>();
        events.add(new Event());
        //MOCK
        when(eventRepository.findAll()).thenReturn(events);
        //EXECUTE
        List<Event> allEvents = eventServiceImpl.getAllEvents();
        //ASSERT
        assertEquals(1, allEvents.size());
    }

    @Test
    public void testAddShowtimeToEvent_Valid() {
        //SETUP
        Event event = new Event();
        Showtime showtime = new Showtime();
        //MOCK
        when(showtimeRepository.save(any(Showtime.class))).thenReturn(showtime);
        //EXECUTE
        Showtime addedShowtime = eventServiceImpl.addShowtimeToEvent(event, showtime, showtimeRepository);
        //ASSERT
        assertNotNull(addedShowtime);
    }

    @Test
    public void testDeleteAll() { //DONT NEED EXISTING STATE OR DATA
        //EXECUTE
        eventServiceImpl.deleteAll();
        //VERIFY
        verify(eventRepository, times(1)).deleteAll();
    }

    @Test
    public void testDeleteEventByEid_Valid() { //DONT NEED EXISTING STATE OR DATA
        //EXECUTE
        eventServiceImpl.deleteEventbyEid(1);
        //VERIFY
        verify(eventRepository, times(1)).deleteEventByEid(1);
    }
}