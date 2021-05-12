package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Movie;

import java.util.ArrayList;

public interface MovieRepository {

    void createMovie(Movie movie) throws Exception;

    ArrayList<Movie> getAllMovies();

    Movie findMovieByMovieTitle(String movieTitle) throws Exception;

    void updateMovieByMovie(Movie movie) throws Exception;

    void deleteMovieByMovieTitle(String movieTitle) throws Exception;

}
