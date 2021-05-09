package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.User;

import java.util.ArrayList;

public interface UserRepository {

    void createUser(User user);

    ArrayList<User> getAllUsers();

    User findUserByUserName(String userName);
}
