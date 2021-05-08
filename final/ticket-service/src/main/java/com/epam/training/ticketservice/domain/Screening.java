package com.epam.training.ticketservice.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Screening {

    private final String movieTitle;
    private final String roomName;
    private final Date screeningTime;

    @Override
    public String toString() {
        return  movieTitle + " - screening in"
                + roomName.toUpperCase() + " at"
                + screeningTime;
    }
}
