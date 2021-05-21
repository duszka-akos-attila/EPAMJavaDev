package com.epam.training.ticketservice.security.configuration;

import com.epam.training.ticketservice.security.session.SessionManagerImplementation;
import com.epam.training.ticketservice.security.session.TokenCollector;
import com.epam.training.ticketservice.security.session.TokenCollectorImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfiguration {

    @Bean
    public TokenCollector tokenCollector() {
        return new TokenCollectorImplementation();
    }

    @Bean
    public BCryptPasswordEncoder typeBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionManagerImplementation sessionManager() {
        return new SessionManagerImplementation(0,true,false);
    }
}
