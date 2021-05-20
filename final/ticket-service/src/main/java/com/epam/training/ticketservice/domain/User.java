package com.epam.training.ticketservice.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class User {

    private final String userName;
    private final String userPassword;
    private final boolean isPrivileged;

    @Override
    public String toString() {
        if (isPrivileged) {
            return  userName + ", privileged";
        }

        return  userName + ", not privileged";
    }
}
