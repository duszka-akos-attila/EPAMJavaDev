package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Movie;

import java.util.ArrayList;

public interface MovieRepository {

    void createMovie(Movie movie);

    ArrayList<Movie> getAllMovies();

    Movie findMovieByTitle();

    void updateMovie(String movieTitle, Movie movie);

    void deleteMovie(String movieTitle);

}
