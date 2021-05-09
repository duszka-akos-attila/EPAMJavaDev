package com.epam.training.ticketservice.dataaccess.init;

import com.epam.training.ticketservice.dataaccess.dao.UserDao;
import com.epam.training.ticketservice.dataaccess.projection.UserProjection;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserDatabaseInitializer {

    private final UserDao userDao;

    public UserDatabaseInitializer(UserDao userDao){
        this.userDao = userDao;
    }

    @PostConstruct
    public void initDatabase() {
        Optional<UserProjection> userProjection = userDao.findByUserName("admin");

        if(userProjection.isEmpty()) {
            userDao.save(
                    new UserProjection(UUID.nameUUIDFromBytes("admin".getBytes()), "admin", "admin", true)
            );
        }
    }
}
