package ua.opnu.practice1_template.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.opnu.practice1_template.model.Event;
import ua.opnu.practice1_template.model.Guest;
import ua.opnu.practice1_template.model.Invitation;
import ua.opnu.practice1_template.service.InvitationService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class InvitationControllerTest {

    private MockMvc mockMvc;
    private InvitationService invitationService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        invitationService = mock(InvitationService.class);
        InvitationController invitationController = new InvitationController();
        org.springframework.test.util.ReflectionTestUtils.setField(invitationController, "invitationService", invitationService);
        mockMvc = MockMvcBuilders.standaloneSetup(invitationController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateInvitation() throws Exception {
        Invitation invitation = new Invitation();
        Invitation savedInvitation = new Invitation();
        savedInvitation.setId(1L);

        when(invitationService.createInvitation(any(Invitation.class))).thenReturn(savedInvitation);

        mockMvc.perform(post("/api/invitations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invitation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateStatus() throws Exception {
        Invitation updated = new Invitation();
        updated.setId(1L);
        updated.setStatus("ACCEPTED");

        when(invitationService.updateStatus(eq(1L), eq("ACCEPTED"))).thenReturn(updated);

        mockMvc.perform(put("/api/invitations/1/status?status=ACCEPTED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ACCEPTED"));
    }

    @Test
    void testDeleteInvitation() throws Exception {
        doNothing().when(invitationService).deleteInvitation(1L);

        mockMvc.perform(delete("/api/invitations/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetInvitationsByEvent() throws Exception {
        when(invitationService.getInvitationsByEventId(1L)).thenReturn(List.of(new Invitation(), new Invitation()));

        mockMvc.perform(get("/api/invitations/event/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetInvitationsByGuest() throws Exception {
        when(invitationService.getInvitationsByGuestId(1L)).thenReturn(List.of(new Invitation()));

        mockMvc.perform(get("/api/invitations/guest/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testGetGuestsByEvent() throws Exception {
        when(invitationService.getGuestsByEventId(1L)).thenReturn(List.of(new Guest(), new Guest()));

        mockMvc.perform(get("/api/invitations/event/1/guests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetEventsByGuest() throws Exception {
        when(invitationService.getEventsByGuestId(1L)).thenReturn(List.of(new Event()));

        mockMvc.perform(get("/api/invitations/guest/1/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testGetGuestCountByEvent() throws Exception {
        when(invitationService.getGuestCountByEventId(1L)).thenReturn(3);

        mockMvc.perform(get("/api/invitations/event/1/guest-count"))
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }
}
