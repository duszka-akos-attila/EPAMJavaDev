package com.epam.training.ticketservice.security.session;

import java.util.ArrayList;
import java.util.UUID;

public interface TokenCollector {

    ArrayList<UUID> getTokens();

    void addToken(UUID token);

    void removeToken(UUID token);
}
