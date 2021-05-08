package com.epam.training.ticketservice.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Room {

    private final String roomName;
    private final int seatRows;
    private final int seatColumns;

    @Override
    public String toString() {
        return  roomName + " - "
                + seatRows + "rows X "
                + seatColumns + "columns";
    }
}
