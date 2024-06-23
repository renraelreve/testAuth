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
        Event event = new Event();
        when(eventRepository.findByDescription(anyString())).thenReturn(event);
        Event foundEvent = eventServiceImpl.findByDescription("Concert");
        assertNotNull(foundEvent);
    }

    @Test
    public void testFindEventByEid_Valid() {
        Event event = new Event();
        when(eventRepository.findEventByEid(anyInt())).thenReturn(event);
        Event foundEvent = eventServiceImpl.findEventByEid(1);
        assertNotNull(foundEvent);
    }

    @Test
    public void testGetAllEvents() {
        List<Event> events = new ArrayList<>();
        events.add(new Event());
        when(eventRepository.findAll()).thenReturn(events);
        List<Event> allEvents = eventServiceImpl.getAllEvents();
        assertEquals(1, allEvents.size());
    }

    @Test
    public void testAddShowtimeToEvent_Valid() {
        Event event = new Event();
        Showtime showtime = new Showtime();
        when(showtimeRepository.save(any(Showtime.class))).thenReturn(showtime);
        Showtime addedShowtime = eventServiceImpl.addShowtimeToEvent(event, showtime, showtimeRepository);
        assertNotNull(addedShowtime);
    }

    @Test
    public void testDeleteAll() {
        eventServiceImpl.deleteAll();
        verify(eventRepository, times(1)).deleteAll();
    }

    @Test
    public void testDeleteEventByEid_Valid() {
        doNothing().when(eventRepository).deleteEventByEid(anyInt());
        eventServiceImpl.deleteEventbyEid(1);
        verify(eventRepository, times(1)).deleteEventByEid(1);
    }
}