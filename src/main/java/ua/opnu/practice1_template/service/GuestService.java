package ua.opnu.practice1_template.service;

import ua.opnu.practice1_template.model.Guest;
import ua.opnu.practice1_template.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    public Guest addGuest(Guest guest) {
        return guestRepository.save(guest);
    }

    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    public void deleteGuest(Long id) {
        guestRepository.deleteById(id);
    }
}
