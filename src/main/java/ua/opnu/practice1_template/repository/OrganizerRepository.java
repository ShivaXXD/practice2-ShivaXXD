package ua.opnu.practice1_template.repository;

import ua.opnu.practice1_template.model.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
}
