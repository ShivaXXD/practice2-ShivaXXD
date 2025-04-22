package ua.opnu.practice1_template.service;

import ua.opnu.practice1_template.model.Event;
import ua.opnu.practice1_template.model.Room;
import ua.opnu.practice1_template.repository.RoomRepository;
import ua.opnu.practice1_template.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private EventRepository eventRepository;
    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public Room updateRoom(Long id, Room updatedRoom) {
        return roomRepository.findById(id).map(room -> {
            room.setName(updatedRoom.getName());
            room.setCapacity(updatedRoom.getCapacity());
            room.setLocation(updatedRoom.getLocation());
            return roomRepository.save(room);
        }).orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    public List<Room> getAvailableRoomsByDate(LocalDate date) {
        try {
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(23, 59, 59);

            List<Event> eventsOnDate = eventRepository.findByDateTimeBetween(startOfDay, endOfDay);
            Set<Room> occupiedRooms = eventsOnDate.stream()
                    .map(Event::getRoom)
                    .collect(Collectors.toSet());

            List<Room> allRooms = roomRepository.findAll();

            return allRooms.stream()
                    .filter(room -> !occupiedRooms.contains(room))
                    .toList();
        } catch (Exception e) {
            System.err.println("Error fetching available rooms: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
