package com.epam.training.ticketservice.dataaccess.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class UserProjection {

    @Id
    @GeneratedValue
    private UUID userId;

    @Column(unique = true)
    private String userName;

    private String userPassword;
    private boolean isPrivileged;
}
