package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Repository
public class JpaMovieRepository implements MovieRepository {

    private final MovieDao movieDao;

    public JpaMovieRepository(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public void createMovie(Movie movie) {
        movieDao.save(new MovieProjection(
                null,
                movie.getMovieTitle(),
                movie.getMovieGenre(),
                movie.getMovieLength()
        ));
    }

    @Override
    public ArrayList<Movie> getAllMovies() {
        return movieDao.findAll().stream()
                .map(movieProjection -> new Movie(
                        movieProjection.getMovieTitle(),
                        movieProjection.getMovieGenre(),
                        movieProjection.getMovieLength()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Movie findMovieByTitle(String movieTitle) throws Exception {
        MovieProjection movieProjection = movieDao.findByMovieTitle(movieTitle).orElseThrow(
                () -> new Exception("Movie not found with \""+ movieTitle +"\" title!")
        );

        return new Movie(
                movieProjection.getMovieTitle(),
                movieProjection.getMovieGenre(),
                movieProjection.getMovieLength());
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {
        MovieProjection movieProjection = movieDao.findByMovieTitle(movie.getMovieTitle()).orElseThrow(
                () -> new Exception("Movie not found with \""+ movie.getMovieTitle() +"\" title!")
        );
        movieProjection.setMovieGenre(movie.getMovieGenre());
        movieProjection.setMovieLength(movie.getMovieLength());
        movieDao.save(movieProjection);
    }

    @Override
    public void deleteMovie(String movieTitle) {
        movieDao.deleteByMovieTitle(movieTitle);
    }
}
