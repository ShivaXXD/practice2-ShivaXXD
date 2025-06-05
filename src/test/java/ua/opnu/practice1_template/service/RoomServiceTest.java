package ua.opnu.practice1_template.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ua.opnu.practice1_template.model.Event;
import ua.opnu.practice1_template.model.Room;
import ua.opnu.practice1_template.repository.EventRepository;
import ua.opnu.practice1_template.repository.RoomRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RoomServiceTest {

    private RoomService roomService;
    private RoomRepository roomRepository;
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        roomRepository = mock(RoomRepository.class);
        eventRepository = mock(EventRepository.class);

        roomService = new RoomService();

        // Вставляємо мок-залежності в приватні поля
        ReflectionTestUtils.setField(roomService, "roomRepository", roomRepository);
        ReflectionTestUtils.setField(roomService, "eventRepository", eventRepository);
    }

    @Test
    void testAddRoom() {
        Room room = new Room(null, "Room A", 100, "Center");
        when(roomRepository.save(room)).thenReturn(room);

        Room result = roomService.addRoom(room);

        assertEquals("Room A", result.getName());
        verify(roomRepository).save(room);
    }

    @Test
    void testGetAllRooms() {
        when(roomRepository.findAll()).thenReturn(List.of(new Room(), new Room()));
        assertEquals(2, roomService.getAllRooms().size());
    }

    @Test
    void testUpdateRoom() {
        Room existing = new Room(1L, "Old", 50, "OldLoc");
        Room updated = new Room(1L, "New", 150, "NewLoc");

        when(roomRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(roomRepository.save(any(Room.class))).thenReturn(updated);

        Room result = roomService.updateRoom(1L, updated);

        assertEquals("New", result.getName());
        verify(roomRepository).save(any(Room.class));
    }

    @Test
    void testDeleteRoom() {
        roomService.deleteRoom(1L);
        verify(roomRepository).deleteById(1L);
    }

    @Test
    void testGetAvailableRoomsByDate() {
        LocalDate date = LocalDate.now();

        Room room1 = new Room(1L, "Room1", 100, "A");
        Room room2 = new Room(2L, "Room2", 150, "B");
        Event event = new Event(1L, "Some Event", date.atTime(10, 0), room1, null);

        when(eventRepository.findByDateTimeBetween(any(), any())).thenReturn(List.of(event));
        when(roomRepository.findAll()).thenReturn(List.of(room1, room2));

        List<Room> result = roomService.getAvailableRoomsByDate(date);

        assertEquals(1, result.size());
        assertEquals("Room2", result.get(0).getName());
    }
}
