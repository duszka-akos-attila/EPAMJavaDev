package com.epam.training.ticketservice.dataaccess.init;

import com.epam.training.ticketservice.dataaccess.dao.UserDao;
import com.epam.training.ticketservice.dataaccess.projection.UserProjection;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserDatabaseInitializer {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserDatabaseInitializer(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initDatabase() {
        if (userDao.findByUserName("admin").isEmpty()) {
            userDao.save(
                    new UserProjection(
                            UUID.nameUUIDFromBytes("admin".getBytes()), "admin", passwordEncoder.encode("admin"), true)
            );
        }
    }
}
