package ua.opnu.practice1_template.repository;

import ua.opnu.practice1_template.model.Event;
import ua.opnu.practice1_template.model.Room;
import ua.opnu.practice1_template.model.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByRoom(Room room);

    List<Event> findByOrganizer(Organizer organizer);

    List<Event> findByDateTime(LocalDateTime dateTime);

    List<Event> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);

}
