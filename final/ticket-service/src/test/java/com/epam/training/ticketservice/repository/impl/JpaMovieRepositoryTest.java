package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.domain.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JpaMovieRepositoryTest {

    private JpaMovieRepository jpaMovieRepository;
    private MovieDao movieDao;

    private final Movie movie = new Movie("Test1", "Test1", 100);
    private final Movie movie2 = new Movie("Test2", "Test2", 200);
    private final MovieProjection movieProjection = new MovieProjection(
            null, movie.getMovieTitle(), movie.getMovieGenre(), movie.getMovieLength());
    private final MovieProjection movieProjection2 = new MovieProjection(
            null, movie2.getMovieTitle(), movie2.getMovieGenre(), movie2.getMovieLength());

    @BeforeEach
    void setUp() {
        movieDao = Mockito.mock(MovieDao.class);
        jpaMovieRepository = new JpaMovieRepository(movieDao);
    }

    @Test
    void testCreateMovieCreatesMovieSuccessfully() throws Exception {
        // Given

        // When
        jpaMovieRepository.createMovie(movie);

        // Then
        Mockito.verify(movieDao, Mockito.times(1)).save(movieProjection);
    }

    @Test
    void testCreateMovieThatAlreadyExistsThrowsException() throws Exception {
        // Given
        Mockito.when(movieDao.findByMovieTitle(movie.getMovieTitle())).thenReturn(Optional.of(movieProjection));
        // When
        Assertions.assertThrows(Exception.class, () -> jpaMovieRepository.createMovie(movie));

        // Then
        Mockito.verify(movieDao, Mockito.times(0)).save(movieProjection);
    }

    @Test
    void testGetAllMoviesShouldReturnSuccessfully() {
        // Given
        ArrayList<Movie> expectedResult = new ArrayList<Movie>(List.of(movie, movie2));
        Mockito.when(movieDao.findAll()).thenReturn(new ArrayList<MovieProjection>(List.of(movieProjection, movieProjection2)));

        // When
        ArrayList<Movie> actualResult = jpaMovieRepository.getAllMovies();

        // Then
        Assertions.assertEquals(expectedResult, actualResult);
        Mockito.verify(movieDao).findAll();
    }

    @Test
    void testFindMovieByMovieTitleReturnSuccessfully() throws Exception {
        // Given
        Mockito.when(movieDao.findByMovieTitle(movie.getMovieTitle())).thenReturn(Optional.of(movieProjection));

        // When
        Assertions.assertEquals(movie, jpaMovieRepository.findMovieByMovieTitle(movie.getMovieTitle()));

        // Then
        Mockito.verify(movieDao).findByMovieTitle(movie.getMovieTitle());
    }

    @Test
    void testFindMovieByMovieThrowsExceptionWhenMovieNotFound() throws Exception {
        // Given
        Mockito.when(movieDao.findByMovieTitle(movie.getMovieTitle())).thenReturn(Optional.empty());

        // When
        Assertions.assertThrows(Exception.class, () -> jpaMovieRepository.findMovieByMovieTitle(movie.getMovieTitle()));

        // Then
        Mockito.verify(movieDao).findByMovieTitle(movie.getMovieTitle());
    }

    @Test
    void testUpdateMovieByMovieUpdatesMovieSuccessfully() throws Exception {
        // Given
        Mockito.when(movieDao.findByMovieTitle(movie.getMovieTitle())).thenReturn(Optional.of(movieProjection));
        Movie updatedMovie = new Movie(movie.getMovieTitle(), movie2.getMovieGenre(), movie2.getMovieLength());
        MovieProjection updatedMovieProjection = new MovieProjection(
                movieProjection.getMovieId(), updatedMovie.getMovieTitle(), updatedMovie.getMovieGenre(), updatedMovie.getMovieLength());
        // When
        jpaMovieRepository.updateMovieByMovie(updatedMovie);

        // Then
        Mockito.verify(movieDao).save(updatedMovieProjection);
    }

    @Test
    void testUpdateMovieByMovieThrowsExceptionWhenMovieNotFound() throws Exception {
        // Given
        Mockito.when(movieDao.findByMovieTitle(movie.getMovieTitle())).thenReturn(Optional.empty());
        Movie updatedMovie = new Movie(movie.getMovieTitle(), movie2.getMovieGenre(), movie2.getMovieLength());
        MovieProjection updatedMovieProjection = new MovieProjection(
                movieProjection.getMovieId(), updatedMovie.getMovieTitle(), updatedMovie.getMovieGenre(), updatedMovie.getMovieLength());
        // When
        Assertions.assertThrows(Exception.class, () -> jpaMovieRepository.updateMovieByMovie(updatedMovie));

        // Then
        Mockito.verify(movieDao, Mockito.times(0)).save(updatedMovieProjection);
    }

    @Test
    void testDeleteMovieByMovieTitleDeletesMovieSuccessfully() throws Exception {
        // Given
        Mockito.when(movieDao.findByMovieTitle(movie.getMovieTitle())).thenReturn(Optional.of(movieProjection));

        // When
        jpaMovieRepository.deleteMovieByMovieTitle(movie.getMovieTitle());

        // Then
        Mockito.verify(movieDao).deleteByMovieTitle(movie.getMovieTitle());
    }

    @Test
    void testDeleteMovieByMovieTitleThrowsExceptionWhenMovieNotFound() throws Exception {
        // Given
        Mockito.when(movieDao.findByMovieTitle(movie.getMovieTitle())).thenReturn(Optional.empty());

        // When
        Assertions.assertThrows(Exception.class, () -> jpaMovieRepository.deleteMovieByMovieTitle(movie.getMovieTitle()));

        // Then
        Mockito.verify(movieDao, Mockito.times(0)).deleteByMovieTitle(movie.getMovieTitle());
    }
}