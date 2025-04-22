package ua.opnu.practice1_template.repository;

import ua.opnu.practice1_template.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
