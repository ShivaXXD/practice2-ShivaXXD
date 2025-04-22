package ua.opnu.practice1_template.controller;

import org.springframework.format.annotation.DateTimeFormat;
import ua.opnu.practice1_template.model.Event;
import ua.opnu.practice1_template.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/room/{roomId}")
    public List<Event> getEventsByRoom(@PathVariable Long roomId) {
        return eventService.getEventsByRoomId(roomId);
    }

    @GetMapping("/organizer/{organizerId}")
    public List<Event> getEventsByOrganizer(@PathVariable Long organizerId) {
        return eventService.getEventsByOrganizerId(organizerId);
    }

    @GetMapping("/date")
    public List<Event> getEventsByDate(@RequestParam String dateTime) {
        return eventService.getEventsByDate(LocalDateTime.parse(dateTime));
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event event) {
        return eventService.updateEvent(id, event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
    @GetMapping("/by-date")
    public List<Event> getEventsByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return eventService.getEventsByDate(date);
    }
}
