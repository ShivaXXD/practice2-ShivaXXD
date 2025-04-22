package ua.opnu.practice1_template.service;

import ua.opnu.practice1_template.model.Invitation;
import ua.opnu.practice1_template.model.Guest;
import ua.opnu.practice1_template.model.Event;
import ua.opnu.practice1_template.repository.InvitationRepository;
import ua.opnu.practice1_template.repository.EventRepository;
import ua.opnu.practice1_template.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvitationService {

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private EventRepository eventRepository;

    public Invitation createInvitation(Invitation invitation) {
        return invitationRepository.save(invitation);
    }

    public Invitation updateStatus(Long id, String status) {
        Invitation invitation = invitationRepository.findById(id).orElseThrow();
        invitation.setStatus(status);
        return invitationRepository.save(invitation);
    }

    public void deleteInvitation(Long id) {
        invitationRepository.deleteById(id);
    }

    public List<Invitation> getInvitationsByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        return invitationRepository.findByEvent(event);
    }

    public List<Invitation> getInvitationsByGuestId(Long guestId) {
        Guest guest = guestRepository.findById(guestId).orElseThrow();
        return invitationRepository.findByGuest(guest);
    }

    public List<Guest> getGuestsByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        List<Invitation> invitations = invitationRepository.findByEvent(event);
        return invitations.stream()
                .map(Invitation::getGuest)
                .toList();
    }

    public List<Event> getEventsByGuestId(Long guestId) {
        Guest guest = guestRepository.findById(guestId).orElseThrow();
        List<Invitation> invitations = invitationRepository.findByGuest(guest);
        return invitations.stream()
                .map(Invitation::getEvent)
                .toList();
    }

    public int getGuestCountByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        return invitationRepository.countByEvent(event);
    }

}
