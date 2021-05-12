package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.User;


public interface UserRepository {

    void createUser(User user) throws Exception;

    User findUserByUserName(String userName) throws Exception;

}
