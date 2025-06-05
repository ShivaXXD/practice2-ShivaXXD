package ua.opnu.practice1_template.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ua.opnu.practice1_template.repository.OrganizerRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrganizerServiceTest {

    private OrganizerService organizerService;
    private OrganizerRepository organizerRepository;

    @BeforeEach
    void setUp() {
        organizerRepository = mock(OrganizerRepository.class);
        organizerService = new OrganizerService();
        ReflectionTestUtils.setField(organizerService, "organizerRepository", organizerRepository);
    }

    @Test
    void testPlaceholder() {
        assertNotNull(organizerService);
    }
}
