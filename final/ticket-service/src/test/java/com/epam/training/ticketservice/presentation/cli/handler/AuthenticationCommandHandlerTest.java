package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.security.service.AuthenticationService;
import com.epam.training.ticketservice.security.session.SessionManager;
import com.epam.training.ticketservice.security.session.TokenCollector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationCommandHandlerTest {

    private AuthenticationService authenticationService;
    private SessionManager sessionManager;
    private TokenCollector tokenCollector;
    private AuthenticationCommandHandler authenticationCommandHandler;

    @BeforeEach
    void setUp() {
        authenticationService = Mockito.mock(AuthenticationService.class);
        sessionManager = Mockito.mock(SessionManager.class);
        tokenCollector = Mockito.mock(TokenCollector.class);
        authenticationCommandHandler = new AuthenticationCommandHandler(
                authenticationService, sessionManager, tokenCollector);
    }

    @Test
    void signInPrivileged() {
    }

    @Test
    void describeAccountWhenUserNotSignedIn() {
        // Given
        Mockito.when(tokenCollector.getTokens()).thenReturn(new ArrayList<UUID>());
        // When
        String result = authenticationCommandHandler.describeAccount();
        // Then
        Assertions.assertEquals("You are not signed in", result);
    }

    @Test
    void describeAccountWhenPrivilegedUserSignedIn() {
        // Given
        Mockito.when(tokenCollector.getTokens()).thenReturn(new ArrayList<UUID>(List.of(UUID.nameUUIDFromBytes("admin".getBytes()))));
        Mockito.when(sessionManager.isPrivilegedSession(UUID.nameUUIDFromBytes("admin".getBytes()))).thenReturn(true);
        Mockito.when(sessionManager.getSessionUsername(UUID.nameUUIDFromBytes("admin".getBytes()))).thenReturn("admin");
        // When
        String result = authenticationCommandHandler.describeAccount();
        // Then
        Assertions.assertEquals("Signed in with privileged account 'admin'", result);
    }

    @Test
    void describeAccountWhenNotPrivilegedUserSignedIn() {
        // Given
        Mockito.when(tokenCollector.getTokens()).thenReturn(new ArrayList<UUID>(List.of(UUID.nameUUIDFromBytes("admin".getBytes()))));
        Mockito.when(sessionManager.isPrivilegedSession(UUID.nameUUIDFromBytes("admin".getBytes()))).thenReturn(false);
        Mockito.when(sessionManager.getSessionUsername(UUID.nameUUIDFromBytes("admin".getBytes()))).thenReturn("admin");
        // When
        String result = authenticationCommandHandler.describeAccount();
        // Then
        Assertions.assertEquals("Signed in with account 'admin'", result);
    }
}