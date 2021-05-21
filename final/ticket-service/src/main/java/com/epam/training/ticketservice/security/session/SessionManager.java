package com.epam.training.ticketservice.security.session;

import java.util.ArrayList;
import java.util.UUID;

public interface SessionManager {

    void setSessionTimeInMinutes(int sessionTimeInMinutes);

    void setAutoRenewSession(boolean autoRenewSession);

    void setMultipleSessionsAllowed(boolean multipleSessionsAllowed);

    UUID createSession(String userName, boolean isSessionPrivileged);

    void killSession(UUID sessionToken);

    void renewSession(UUID sessionToken);

    boolean isSessionAlive(UUID sessionToken);

    boolean isPrivilegedSession(UUID sessionToken);

    ArrayList<Session> findSessionByUserName(String userName);

    Session findSessionByToken(UUID sessionToken);

    String getSessionUsername(UUID sessionToken);
}
