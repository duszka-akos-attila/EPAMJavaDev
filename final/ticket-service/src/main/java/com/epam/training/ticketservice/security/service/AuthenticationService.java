package com.epam.training.ticketservice.security.service;

import com.epam.training.ticketservice.domain.User;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.security.session.SessionManager;
import com.epam.training.ticketservice.security.session.TokenCollector;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionManager sessionManager;
    private final TokenCollector tokenCollector;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            SessionManager sessionManager,
            TokenCollector tokenCollector) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.sessionManager = sessionManager;
        this.tokenCollector = tokenCollector;
    }

    public int signIn(String userName, String userPassword) {
        User user;
        UUID sessionToken = null;
        try {
            user = findUserByUserName(userName);
        } catch (Exception ignored) {
            return -1;
        }
        if (passwordEncoder.matches(userPassword,user.getUserPassword())) {
            sessionToken = sessionManager.createSession(userName, user.isPrivileged());
            if (sessionToken != null) {
                tokenCollector.addToken(sessionToken);
            } else {
                return -3;
            }

            return 0;
        }
        return -2;
    }

    public void signOut() {
        sessionManager.killSession(tokenCollector.getTokens().get(tokenCollector.getTokens().size() - 1));
    }

    public User findUserByUserName(String userName) throws Exception {
        return userRepository.findUserByUserName(userName);
    }
}
