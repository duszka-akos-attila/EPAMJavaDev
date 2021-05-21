package com.epam.training.ticketservice.domain;

import com.epam.training.ticketservice.utilities.converter.DateConverter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Screening {

    private final Movie movie;
    private final Room room;
    private final Date screeningTime;

    @Override
    public String toString() {
        return movie.toString() + ", screened in room "
                + room.getRoomName() + ", at "
                + new DateConverter().convertDateToString(screeningTime);
    }
}
