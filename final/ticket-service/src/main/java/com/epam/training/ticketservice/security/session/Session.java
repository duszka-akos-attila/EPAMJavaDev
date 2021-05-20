package com.epam.training.ticketservice.security.session;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

public class Session {
    public final UUID token;
    private final String userName;
    private Date timeOfDeath;
    private final boolean privilegedSession;

    Session(String userName, int sessionTimeInMinutes, boolean isSessionPrivileged) {
        this.token = UUID.randomUUID();
        this.userName = userName;
        this.timeOfDeath = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)
                .plus(Duration.ofMinutes(sessionTimeInMinutes)));
        this.privilegedSession = isSessionPrivileged;
    }

    public UUID getToken() {
        return token;
    }

    String getUserName() {
        return userName;
    }

    Date getTimeOfDeath() {
        return timeOfDeath;
    }

    void setTimeOfDeath(Date timeOfDeath) {
        this.timeOfDeath = timeOfDeath;
    }

    boolean isPrivilegedSession() {
        return privilegedSession;
    }
}
