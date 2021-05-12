package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void createMovie(String movieTitle, String movieGenre, int movieLength) throws Exception {
        movieRepository.createMovie(new Movie(
                movieTitle, movieGenre, movieLength
        ));
    }

    public ArrayList<Movie> getAllMovies() {
        return movieRepository.getAllMovies();
    }

    public void updateMovie(String movieTitle, String movieGenre, int movieLength) throws Exception {
        movieRepository.updateMovieByMovie(new Movie(
                movieTitle, movieGenre, movieLength
        ));
    }

    public void deleteMovie(String movieTitle) throws Exception {
        movieRepository.deleteMovieByMovieTitle(movieTitle);
    }

}
