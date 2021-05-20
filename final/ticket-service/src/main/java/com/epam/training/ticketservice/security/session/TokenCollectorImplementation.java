package com.epam.training.ticketservice.security.session;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class TokenCollectorImplementation implements TokenCollector {

    ArrayList<UUID> tokens = new ArrayList<>();

    @Override
    public ArrayList<UUID> getTokens() {
        return tokens;
    }

    @Override
    public void addToken(UUID token) {
        tokens.add(token);
    }

    @Override
    public void removeToken(UUID token) {
        tokens.removeIf(ltoken -> ltoken == token);
    }
}
