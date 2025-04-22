package ua.opnu.practice1_template.service;

import ua.opnu.practice1_template.model.Event;
import ua.opnu.practice1_template.model.Room;
import ua.opnu.practice1_template.model.Organizer;
import ua.opnu.practice1_template.repository.EventRepository;
import ua.opnu.practice1_template.repository.RoomRepository;
import ua.opnu.practice1_template.repository.OrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private OrganizerRepository organizerRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getEventsByRoomId(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        return eventRepository.findByRoom(room);
    }

    public List<Event> getEventsByOrganizerId(Long organizerId) {
        Organizer organizer = organizerRepository.findById(organizerId).orElseThrow();
        return eventRepository.findByOrganizer(organizer);
    }

    public List<Event> getEventsByDate(LocalDateTime dateTime) {
        return eventRepository.findByDateTime(dateTime);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        return eventRepository.findById(id).map(event -> {
            event.setName(updatedEvent.getName());
            event.setDateTime(updatedEvent.getDateTime());
            event.setRoom(updatedEvent.getRoom());
            event.setOrganizer(updatedEvent.getOrganizer());
            return eventRepository.save(event);
        }).orElseThrow();
    }
    public List<Event> getEventsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        return eventRepository.findByDateTimeBetween(startOfDay, endOfDay);
    }
}
