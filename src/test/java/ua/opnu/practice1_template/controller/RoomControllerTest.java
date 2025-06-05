package ua.opnu.practice1_template.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.opnu.practice1_template.model.Room;
import ua.opnu.practice1_template.service.RoomService;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RoomControllerTest {

    private MockMvc mockMvc;
    private RoomService roomService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        roomService = mock(RoomService.class);
        RoomController roomController = new RoomController();
        org.springframework.test.util.ReflectionTestUtils.setField(roomController, "roomService", roomService);
        mockMvc = MockMvcBuilders.standaloneSetup(roomController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAddRoom() throws Exception {
        Room room = new Room();
        room.setName("Test Room");
        room.setCapacity(20);
        room.setLocation("Main Hall");

        Room saved = new Room();
        saved.setId(1L);
        saved.setName("Test Room");
        saved.setCapacity(20);
        saved.setLocation("Main Hall");

        when(roomService.addRoom(any(Room.class))).thenReturn(saved);

        mockMvc.perform(post("/api/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateRoom() throws Exception {
        Room updated = new Room();
        updated.setId(1L);
        updated.setName("Updated Room");
        updated.setCapacity(50);
        updated.setLocation("Updated Hall");

        when(roomService.updateRoom(eq(1L), any(Room.class))).thenReturn(updated);

        mockMvc.perform(put("/api/rooms/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Room"));
    }

    @Test
    void testDeleteRoom() throws Exception {
        doNothing().when(roomService).deleteRoom(1L);

        mockMvc.perform(delete("/api/rooms/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllRooms() throws Exception {
        Room room1 = new Room();
        room1.setId(1L);
        Room room2 = new Room();
        room2.setId(2L);

        when(roomService.getAllRooms()).thenReturn(List.of(room1, room2));

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetAvailableRooms() throws Exception {
        Room available = new Room();
        available.setId(3L);

        when(roomService.getAvailableRoomsByDate(LocalDate.parse("2025-06-05")))
                .thenReturn(List.of(available));

        mockMvc.perform(get("/api/rooms/available")
                        .param("date", "2025-06-05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
