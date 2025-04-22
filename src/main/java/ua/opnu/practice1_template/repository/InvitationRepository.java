package ua.opnu.practice1_template.repository;

import ua.opnu.practice1_template.model.Invitation;
import ua.opnu.practice1_template.model.Event;
import ua.opnu.practice1_template.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    int countByEvent(Event event);

    List<Invitation> findByEvent(Event event);

    List<Invitation> findByGuest(Guest guest);
}
