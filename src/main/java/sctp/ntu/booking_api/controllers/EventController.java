package sctp.ntu.booking_api.controllers;

import java.util.ArrayList;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sctp.ntu.booking_api.entities.Event;
import sctp.ntu.booking_api.services.EventService;

@RestController
@Transactional
public class EventController {

    private EventService eventService;

    // @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // @GetMapping("/search")
    // public ResponseEntity<ArrayList<Event>> searchEvent(@RequestParam String description) {
    //     ArrayList<Event> foundEvents = eventService.searchEvent(description);
    //     return new ResponseEntity<>(foundEvents, HttpStatus.OK);
    // }

    // CREATE
    // @PostMapping("")
    // public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event) {
        
    //     // if(bindingResult.hasErrors()) {
    //     //     return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    //     // }
        
    //     Event newEvent = eventService.createEvent(event);
    //     return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    // }

    // READ (GET ALL)
    @GetMapping("/events")
    public ResponseEntity<ArrayList<Event>> getAllEvents() {
        ArrayList<Event> allEvents = eventService.getAllEvents();
        return new ResponseEntity<>(allEvents, HttpStatus.OK);
    }

    // READ (GET ONE)
    @GetMapping("/event/{id}")
    public ResponseEntity<Event> getEventByEid(@PathVariable (name = "id") Integer eid) {
        Event foundEvent = eventService.findEventByEid(eid);
        return new ResponseEntity<>(foundEvent, HttpStatus.OK);
    }

    // UPDATE
    // @PutMapping("/{id}")
    // public ResponseEntity<Event> updateEvent(@PathVariable (name = "id") Integer eid, @RequestBody Event event) {
    //     Event updatedEvent = eventService.updateEvent(eid, event);
    //     return new ResponseEntity<>(updatedEvent, HttpStatus.OK);        
    // }

    @DeleteMapping("/events")
    public ResponseEntity<Event> deleteAllEvents() {
        eventService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @DeleteMapping("/event/{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable (name = "id") Integer eid) {
        eventService.deleteEventbyEid(eid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
