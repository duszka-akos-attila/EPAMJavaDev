package com.epam.training.ticketservice.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Movie {

    private final String movieTitle;
    private final String movieGenre;
    private final int movieLength;

    @Override
    public String toString() {
        return  movieTitle + " ("
                + movieGenre.toLowerCase() + ", "
                + movieLength + " minutes)";
    }
}
