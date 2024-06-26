package sctp.ntu.booking_api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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
import sctp.ntu.booking_api.exceptions.EventNotFoundException;
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
        //Setup
        Event event = new Event();
        //Mock
        when(eventRepository.findByDescription(anyString())).thenReturn(event);
        //Execute
        Event foundEvent = eventServiceImpl.findByDescription("Concert");
        //Assert
        assertNotNull(foundEvent);
    }

    @Test
    public void testFindEventByEid_Valid() {
        //Setup
        Event event = new Event();
        //Mock
        when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event));
        //Execute
        Event foundEvent = eventServiceImpl.findEventByEid(1);
        //Assert
        assertNotNull(foundEvent);
    }

    @Test
    public void testGetAllEvents() {
        //Setup
        List<Event> events = new ArrayList<>();
        events.add(new Event());
        //Mock
        when(eventRepository.findAll()).thenReturn(events);
        //Execute
        List<Event> allEvents = eventServiceImpl.getAllEvents();
        //Assert
        assertEquals(1, allEvents.size());
    }

    @Test
    public void testAddShowtimeToEvent_Valid() {
        //Setup
        Event event = new Event();
        Showtime showtime = new Showtime();
        //Mock
        when(showtimeRepository.save(any(Showtime.class))).thenReturn(showtime);
        //Execute
        Showtime addedShowtime = eventServiceImpl.addShowtimeToEvent(event, showtime, showtimeRepository);
        //Assert
        assertNotNull(addedShowtime);
    }

    @Test
    public void testDeleteAll() {
        //Execute
        eventServiceImpl.deleteAll();
        //Verify
        verify(eventRepository, times(1)).deleteAll();
    }

    @Test
    public void testDeleteEventByEid_Valid() {
        //Setup
        Event event = new Event();
        Optional<Event> optionalEvent = Optional.of(event);
        //Mock
        when(eventRepository.findById(anyInt())).thenReturn(optionalEvent);
        doNothing().when(eventRepository).deleteById(anyInt());
        //Execute
        eventServiceImpl.deleteEventbyEid(1);
        //Verify
        verify(eventRepository, times(1)).findById(1);
        verify(eventRepository, times(1)).deleteById(1);
    }
}
