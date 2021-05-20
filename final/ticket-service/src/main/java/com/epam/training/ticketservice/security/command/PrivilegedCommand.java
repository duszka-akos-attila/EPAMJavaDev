package com.epam.training.ticketservice.security.command;

import com.epam.training.ticketservice.security.session.SessionManager;
import com.epam.training.ticketservice.security.session.TokenCollector;
import org.springframework.shell.Availability;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public abstract class PrivilegedCommand extends SecuredCommand {
    private final SessionManager sessionManager;
    private final TokenCollector tokenCollector;

    public PrivilegedCommand(SessionManager sessionManager, TokenCollector tokenCollector) {
        super(tokenCollector, sessionManager);
        this.sessionManager = sessionManager;
        this.tokenCollector = tokenCollector;
    }


    public Availability isUserPrivileged() {
        ArrayList<UUID> tokens = tokenCollector.getTokens();
        if (tokens.size() > 0) {
            if (sessionManager.isPrivilegedSession(tokens.get(tokens.size() - 1))) {
                return Availability.available();
            }
        }
        return Availability.unavailable("Please sign in to be able to use this command!");
    }
}
