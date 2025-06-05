package ua.opnu.practice1_template.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.opnu.practice1_template.model.Event;
import ua.opnu.practice1_template.service.EventService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class EventControllerTest {

    private MockMvc mockMvc;
    private EventService eventService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        eventService = mock(EventService.class);
        EventController eventController = new EventController();
        org.springframework.test.util.ReflectionTestUtils.setField(eventController, "eventService", eventService);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllEvents() throws Exception {
        when(eventService.getAllEvents()).thenReturn(List.of(new Event(), new Event()));

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testCreateEvent() throws Exception {
        Event event = new Event();
        event.setName("Sample Event");

        Event saved = new Event();
        saved.setId(1L);
        event.setName("Sample Event");

        when(eventService.createEvent(any(Event.class))).thenReturn(saved);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testDeleteEvent() throws Exception {
        doNothing().when(eventService).deleteEvent(1L);

        mockMvc.perform(delete("/api/events/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetEventsByRoom() throws Exception {
        when(eventService.getEventsByRoomId(5L)).thenReturn(List.of(new Event()));

        mockMvc.perform(get("/api/events/room/5"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetEventsByOrganizer() throws Exception {
        when(eventService.getEventsByOrganizerId(3L)).thenReturn(List.of(new Event()));

        mockMvc.perform(get("/api/events/organizer/3"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetEventsByDateTime() throws Exception {
        String datetime = "2025-06-05T12:00:00";
        when(eventService.getEventsByDate(LocalDateTime.parse(datetime))).thenReturn(List.of(new Event()));

        mockMvc.perform(get("/api/events/date").param("dateTime", datetime))
                .andExpect(status().isOk());
    }

    @Test
    void testGetEventsByDate() throws Exception {
        String date = "2025-06-05";
        when(eventService.getEventsByDate(LocalDate.parse(date))).thenReturn(List.of(new Event()));

        mockMvc.perform(get("/api/events/by-date").param("date", date))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateEvent() throws Exception {
        Event updated = new Event();
        updated.setId(1L);
        updated.setName("Updated");

        when(eventService.updateEvent(eq(1L), any(Event.class))).thenReturn(updated);

        mockMvc.perform(put("/api/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }
}
