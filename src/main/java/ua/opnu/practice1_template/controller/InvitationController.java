package ua.opnu.practice1_template.controller;

import ua.opnu.practice1_template.model.Event;
import ua.opnu.practice1_template.model.Guest;
import ua.opnu.practice1_template.model.Invitation;
import ua.opnu.practice1_template.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    @Autowired
    private InvitationService invitationService;

    @PostMapping
    public Invitation createInvitation(@RequestBody Invitation invitation) {
        return invitationService.createInvitation(invitation);
    }

    @PutMapping("/{id}/status")
    public Invitation updateStatus(@PathVariable Long id, @RequestParam String status) {
        return invitationService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteInvitation(@PathVariable Long id) {
        invitationService.deleteInvitation(id);
    }

    @GetMapping("/event/{eventId}")
    public List<Invitation> getInvitationsByEvent(@PathVariable Long eventId) {
        return invitationService.getInvitationsByEventId(eventId);
    }

    @GetMapping("/guest/{guestId}")
    public List<Invitation> getInvitationsByGuest(@PathVariable Long guestId) {
        return invitationService.getInvitationsByGuestId(guestId);
    }

    @GetMapping("/event/{eventId}/guests")
    public List<Guest> getGuestsByEvent(@PathVariable Long eventId) {
        return invitationService.getGuestsByEventId(eventId);
    }
    @GetMapping("/guest/{guestId}/events")
    public List<Event> getEventsByGuest(@PathVariable Long guestId) {
        return invitationService.getEventsByGuestId(guestId);
    }

    @GetMapping("/event/{eventId}/guest-count")
    public int getGuestCountByEvent(@PathVariable Long eventId) {
        return invitationService.getGuestCountByEventId(eventId);
    }

}
