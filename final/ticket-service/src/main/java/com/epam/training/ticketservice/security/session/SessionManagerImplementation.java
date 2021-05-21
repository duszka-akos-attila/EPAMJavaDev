package com.epam.training.ticketservice.security.session;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class SessionManagerImplementation implements SessionManager {
    private int sessionTimeInMinutes;
    private boolean autoRenewSession;
    private boolean multipleSessionsAllowed;
    private ArrayList<Session> currentSessions = new ArrayList<>();

    /** This constructor creates a new instance of a SessionManagerImplementation.
     *
     * @param sessionTimeInMinutes The time a Session can be alive in minutes. If set to less than 1,
     *      a Session is only killed, if the {@link #killSession(UUID)} method is called.
     * @param autoRenewSession If set TRUE, the Session always gets renewed,
     *      when the {@link #isSessionAlive(UUID)} method is called, by the sessionTimeInMinutes defined time is
     *      added to the time, when the {@link #isSessionAlive(UUID)} method gets called.
     * @param multipleSessionsAllowed If set TRUE, one user can have multiple Sessions alive.
     */

    public SessionManagerImplementation(
            int sessionTimeInMinutes, boolean autoRenewSession, boolean multipleSessionsAllowed) {
        if (sessionTimeInMinutes < 0) {
            sessionTimeInMinutes = 0;
        }
        this.sessionTimeInMinutes = sessionTimeInMinutes;
        this.autoRenewSession = autoRenewSession;
        this.multipleSessionsAllowed = multipleSessionsAllowed;
    }

    public SessionManagerImplementation() {
        this.sessionTimeInMinutes = 5;
        this.autoRenewSession = true;
        this.multipleSessionsAllowed = false;
    }

    @Override
    public void setSessionTimeInMinutes(int sessionTimeInMinutes) {
        this.sessionTimeInMinutes = sessionTimeInMinutes;
    }

    @Override
    public void setAutoRenewSession(boolean autoRenewSession) {
        this.autoRenewSession = autoRenewSession;
    }

    @Override
    public void setMultipleSessionsAllowed(boolean multipleSessionsAllowed) {
        this.multipleSessionsAllowed = multipleSessionsAllowed;
    }

    @Override
    public UUID createSession(String userName, boolean isSessionPrivileged) {
        if (canCreateNewSessionForUser(userName)) {
            Session session = new Session(userName, sessionTimeInMinutes, isSessionPrivileged);
            currentSessions.add(session);
            return session.getToken();
        } else {
            return null;
        }
    }

    @Override
    public void killSession(UUID sessionToken) {
        currentSessions.removeIf(session -> session.getToken() == sessionToken);
    }

    @Override
    public void renewSession(UUID sessionToken) {
        if (autoRenewSession) {
            Session session = findSessionByToken(sessionToken);

            if (session != null) {
                Date newDate = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)
                        .plus(Duration.ofMinutes(sessionTimeInMinutes)));

                session.setTimeOfDeath(newDate);
            }
        }
    }

    @Override
    public boolean isSessionAlive(UUID sessionToken) {
        Session session = findSessionByToken(sessionToken);
        Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));

        if (session != null) {
            if (sessionTimeInMinutes < 1) {
                return true;
            } else if (now.before(session.getTimeOfDeath())) {
                renewSession(sessionToken);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isPrivilegedSession(UUID sessionToken) {
        Session session = findSessionByToken(sessionToken);
        if (session != null) {
            return session.isPrivilegedSession();
        }
        return false;
    }

    @Override
    public ArrayList<Session> findSessionByUserName(String userName) {
        int sessionsFound = 0;
        ArrayList<Session> resultSession = new ArrayList<>();

        for (Session session: currentSessions) {
            if (session.getUserName().equals(userName)) {
                sessionsFound++;
                resultSession.add(session);
            }
        }

        return resultSession;
    }

    @Override
    public Session findSessionByToken(UUID sessionToken) {
        Session resultSession = null;

        for (Session session: currentSessions) {
            if (session.getToken() == sessionToken) {
                resultSession = session;
            }
        }
        return resultSession;
    }

    @Override
    public String getSessionUsername(UUID sessionToken) {
        return findSessionByToken(sessionToken).getUserName();
    }

    private boolean canCreateNewSessionForUser(String userName) {
        return multipleSessionsAllowed
                || findSessionByUserName(userName).size() < 1;
    }
}
