package ua.opnu.practice1_template.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ua.opnu.practice1_template.repository.GuestRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class GuestServiceTest {

    private GuestService guestService;
    private GuestRepository guestRepository;

    @BeforeEach
    void setUp() {
        guestRepository = mock(GuestRepository.class);
        guestService = new GuestService();
        ReflectionTestUtils.setField(guestService, "guestRepository", guestRepository);
    }

    @Test
    void testPlaceholder() {
        assertNotNull(guestService);
    }
}
