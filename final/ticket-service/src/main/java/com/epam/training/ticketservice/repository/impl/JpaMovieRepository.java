package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Repository
public class JpaMovieRepository implements MovieRepository {

    private final MovieDao movieDao;

    public JpaMovieRepository(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public void createMovie(Movie movie) throws Exception {
        if (movieDao.findByMovieTitle(movie.getMovieTitle()).isEmpty()) {
            movieDao.save(new MovieProjection(
                    null,
                    movie.getMovieTitle(),
                    movie.getMovieGenre(),
                    movie.getMovieLength()
            ));
        } else {
            throw new Exception("Movie with title '" + movie.getMovieTitle() + "' is already exists!");
        }
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
    public Movie findMovieByMovieTitle(String movieTitle) throws Exception {
        MovieProjection movieProjection = movieDao.findByMovieTitle(movieTitle).orElseThrow(
            () -> new Exception("Movie not found with '" + movieTitle + "' title!")
        );

        return new Movie(
                movieProjection.getMovieTitle(),
                movieProjection.getMovieGenre(),
                movieProjection.getMovieLength());
    }

    @Override
    public void updateMovieByMovie(Movie movie) throws Exception {
        MovieProjection movieProjection = movieDao.findByMovieTitle(movie.getMovieTitle()).orElseThrow(
            () -> new Exception("Movie not found with '" + movie.getMovieTitle() + "' title!")
        );
        movieProjection.setMovieGenre(movie.getMovieGenre());
        movieProjection.setMovieLength(movie.getMovieLength());
        movieDao.save(movieProjection);
    }

    @Override
    @Transactional
    public void deleteMovieByMovieTitle(String movieTitle) throws Exception {
        if (movieDao.findByMovieTitle(movieTitle).isEmpty()) {
            throw new Exception("Movie not found with '" + movieTitle + "' title!");
        } else {
            movieDao.deleteByMovieTitle(movieTitle);
        }
    }
}
