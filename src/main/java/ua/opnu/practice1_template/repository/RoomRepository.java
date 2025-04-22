package ua.opnu.practice1_template.repository;

import ua.opnu.practice1_template.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
