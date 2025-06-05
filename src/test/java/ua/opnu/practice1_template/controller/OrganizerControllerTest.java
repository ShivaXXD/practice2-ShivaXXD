package ua.opnu.practice1_template.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.opnu.practice1_template.model.Organizer;
import ua.opnu.practice1_template.service.OrganizerService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrganizerControllerTest {

    private MockMvc mockMvc;
    private OrganizerService organizerService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        organizerService = mock(OrganizerService.class);
        OrganizerController organizerController = new OrganizerController();
        org.springframework.test.util.ReflectionTestUtils.setField(organizerController, "organizerService", organizerService);
        mockMvc = MockMvcBuilders.standaloneSetup(organizerController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAddOrganizer() throws Exception {
        Organizer organizer = new Organizer();
        organizer.setName("Test Organizer");
        organizer.setContactEmail("test@organizer.com");

        Organizer saved = new Organizer();
        saved.setId(1L);
        saved.setName("Test Organizer");
        saved.setContactEmail("test@organizer.com");

        when(organizerService.addOrganizer(any(Organizer.class))).thenReturn(saved);

        mockMvc.perform(post("/api/organizers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(organizer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetAllOrganizers() throws Exception {
        Organizer org1 = new Organizer();
        org1.setId(1L);
        Organizer org2 = new Organizer();
        org2.setId(2L);

        when(organizerService.getAllOrganizers()).thenReturn(List.of(org1, org2));

        mockMvc.perform(get("/api/organizers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testDeleteOrganizer() throws Exception {
        doNothing().when(organizerService).deleteOrganizer(1L);

        mockMvc.perform(delete("/api/organizers/1"))
                .andExpect(status().isOk());
    }
}
