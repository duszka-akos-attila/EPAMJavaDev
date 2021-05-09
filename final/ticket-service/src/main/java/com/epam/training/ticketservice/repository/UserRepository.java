package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.User;

import java.util.ArrayList;

public interface UserRepository {

    void createUser(User user);

    User findUserByUserName(String userName) throws Exception;

}
