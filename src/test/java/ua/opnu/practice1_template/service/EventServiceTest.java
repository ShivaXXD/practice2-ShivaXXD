package ua.opnu.practice1_template.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ua.opnu.practice1_template.repository.EventRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest {

    private EventService eventService;
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        eventRepository = mock(EventRepository.class);
        eventService = new EventService();
        ReflectionTestUtils.setField(eventService, "eventRepository", eventRepository);
    }

    @Test
    void testPlaceholder() {
        assertNotNull(eventService);
    }
}
