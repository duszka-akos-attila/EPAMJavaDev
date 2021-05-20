package com.epam.training.ticketservice.security.command;

import com.epam.training.ticketservice.security.session.Session;
import com.epam.training.ticketservice.security.session.SessionManager;
import com.epam.training.ticketservice.security.session.TokenCollector;
import org.springframework.shell.Availability;

import java.util.ArrayList;
import java.util.UUID;

public abstract class SecuredCommand {
    private final TokenCollector tokenCollector;
    private final SessionManager sessionManager;

    public SecuredCommand(TokenCollector tokenCollector, SessionManager sessionManager) {
        this.tokenCollector = tokenCollector;
        this.sessionManager = sessionManager;
    }

    public Availability isUserSignedIn() {
        ArrayList<UUID> tokens = tokenCollector.getTokens();
        if (tokens.size() > 0) {
            if (sessionManager.isSessionAlive(tokens.get(tokens.size() - 1))) {
                return Availability.available();
            }
        }
        return Availability.unavailable("Please sign in to be able to use this command!");
    }

    public Availability isUserNotSignedIn() {
        ArrayList<UUID> tokens = tokenCollector.getTokens();
        if (tokens.size() > 0) {
            return Availability.unavailable("You can't use this command if you are signed in!");
        }
        return Availability.available();
    }
}
