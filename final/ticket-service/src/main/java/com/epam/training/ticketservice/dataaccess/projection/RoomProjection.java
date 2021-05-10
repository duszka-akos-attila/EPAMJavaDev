package com.epam.training.ticketservice.dataaccess.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class RoomProjection {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long roomId;

    @Column(unique = true)
    private String roomName;
    private int seatRows;
    private int seatColumns;
}
