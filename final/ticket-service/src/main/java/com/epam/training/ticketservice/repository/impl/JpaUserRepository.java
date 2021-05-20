package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.UserDao;
import com.epam.training.ticketservice.dataaccess.projection.UserProjection;
import com.epam.training.ticketservice.domain.User;
import com.epam.training.ticketservice.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaUserRepository implements UserRepository {

    private final UserDao userDao;

    public JpaUserRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findUserByUserName(String userName) throws Exception {
        UserProjection userProjection = userDao.findByUserName(userName).orElseThrow(
            () -> new Exception("User not found with \"" + userName + "\" username!")
        );

        return new User(userProjection.getUserName(),
                userProjection.getUserPassword(),
                userProjection.isPrivileged());
    }

    @Override
    public void createUser(User user) throws Exception {
        if (userDao.findByUserName(user.getUserName()).isEmpty()) {
            userDao.save(new UserProjection(
                    UUID.nameUUIDFromBytes(user.getUserName().getBytes()),
                    user.getUserName(),
                    user.getUserPassword(),
                    user.isPrivileged()
            ));
        } else {
            throw new Exception("Username '" + user.getUserName() + "' is taken already!");
        }
    }
}