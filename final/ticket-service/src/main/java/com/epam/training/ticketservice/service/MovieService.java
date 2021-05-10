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

    public void createMovie(String movieTitle, String movieGenre, int movieLength){
        movieRepository.createMovie(new Movie(
                movieTitle, movieGenre, movieLength
        ));
    }

    public void listMovies() {
        ArrayList<Movie> movies = movieRepository.getAllMovies();
        if(movies.isEmpty()) {
            System.out.println("There are no movies at the moment");
        }
        else {
            for (Movie movie : movies) {
                System.out.println(movie.toString());
            }
        }
    }

    public void updateMovie(String movieTitle, String movieGenre, int movieLength) throws Exception {
        movieRepository.updateMovie(new Movie(
                movieTitle, movieGenre, movieLength
        ));
    }

    public void deleteMovie(String movieTitle){
        movieRepository.deleteMovie(movieTitle);
    }

}
