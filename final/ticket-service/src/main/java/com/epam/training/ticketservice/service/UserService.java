package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.User;
import com.epam.training.ticketservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String userName, String userPassword, boolean isPrivileged) throws Exception {
        userRepository.createUser(new User(
                userName, userPassword, isPrivileged
        ));
    }
}
