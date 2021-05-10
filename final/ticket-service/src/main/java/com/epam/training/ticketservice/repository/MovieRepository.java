package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Movie;

import java.util.ArrayList;

public interface MovieRepository {

    void createMovie(Movie movie);

    ArrayList<Movie> getAllMovies();

    Movie findMovieByTitle(String movieTitle) throws Exception;

    void updateMovie(Movie movie) throws Exception;

    void deleteMovie(String movieTitle);

}
