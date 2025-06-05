package ua.opnu.practice1_template.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ua.opnu.practice1_template.repository.InvitationRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class InvitationServiceTest {

    private InvitationService invitationService;
    private InvitationRepository invitationRepository;

    @BeforeEach
    void setUp() {
        invitationRepository = mock(InvitationRepository.class);
        invitationService = new InvitationService();
        ReflectionTestUtils.setField(invitationService, "invitationRepository", invitationRepository);
    }

    @Test
    void testPlaceholder() {
        assertNotNull(invitationService);
    }
}
