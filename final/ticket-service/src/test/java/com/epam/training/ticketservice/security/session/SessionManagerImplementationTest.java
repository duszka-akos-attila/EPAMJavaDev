package com.epam.training.ticketservice.security.session;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SessionManagerImplementationTest {

    private SessionManagerImplementation sessionManagerImplementation;

    @BeforeEach
    void setUp() {
        sessionManagerImplementation = new SessionManagerImplementation();
    }

    @Test
    void testCreateSessionCreatesSessionSuccessfully() {
        // Given

        // When
        UUID sessionId = sessionManagerImplementation.createSession("admin", true);

        // Then
        Assertions.assertNotEquals(sessionId, null);
    }
    @Test
    void isSessionAliveReturnsFalseOnNull() {
        // Given

        // When
        assertFalse(sessionManagerImplementation.isSessionAlive(null));

        // Then
    }
}