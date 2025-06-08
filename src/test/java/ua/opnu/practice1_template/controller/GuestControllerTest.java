package ua.opnu.practice1_template.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.opnu.practice1_template.model.Guest;
import ua.opnu.practice1_template.service.GuestService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class GuestControllerTest {

    private MockMvc mockMvc;
    private GuestService guestService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        guestService = mock(GuestService.class);
        GuestController guestController = new GuestController();
        org.springframework.test.util.ReflectionTestUtils.setField(guestController, "guestService", guestService);
        mockMvc = MockMvcBuilders.standaloneSetup(guestController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllGuests() throws Exception {
        when(guestService.getAllGuests()).thenReturn(List.of(new Guest(), new Guest()));

        mockMvc.perform(get("/api/guests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testAddGuest() throws Exception {
        Guest guest = new Guest(null, "Test User", "test@example.com");
        Guest saved = new Guest(1L, "Test User", "test@example.com");

        when(guestService.addGuest(any(Guest.class))).thenReturn(saved);

        mockMvc.perform(post("/api/guests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(guest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testDeleteGuest() throws Exception {
        doNothing().when(guestService).deleteGuest(1L);

        mockMvc.perform(delete("/api/guests/1"))
                .andExpect(status().isOk());
    }
}
