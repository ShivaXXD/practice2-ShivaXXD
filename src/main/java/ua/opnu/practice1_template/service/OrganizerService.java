package ua.opnu.practice1_template.service;

import ua.opnu.practice1_template.model.Organizer;
import ua.opnu.practice1_template.repository.OrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizerService {

    @Autowired
    private OrganizerRepository organizerRepository;

    public Organizer addOrganizer(Organizer organizer) {
        return organizerRepository.save(organizer);
    }

    public List<Organizer> getAllOrganizers() {
        return organizerRepository.findAll();
    }

    public void deleteOrganizer(Long id) {
        organizerRepository.deleteById(id);
    }
}
