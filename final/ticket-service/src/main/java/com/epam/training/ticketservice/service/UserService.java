package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.User;
import com.epam.training.ticketservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String userName, String userPassword, boolean isPrivileged) {
        userRepository.createUser(new User(
                userName, userPassword, isPrivileged
        ));
    }
}
