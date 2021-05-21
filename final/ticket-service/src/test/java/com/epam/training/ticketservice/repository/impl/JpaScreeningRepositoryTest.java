package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.dao.ScreeningDao;
import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import com.epam.training.ticketservice.dataaccess.projection.ScreeningProjection;
import com.epam.training.ticketservice.dataaccess.projection.compositekey.ScreeningCompositeKey;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.utilities.converter.DateConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JpaScreeningRepositoryTest {

    private JpaScreeningRepository jpaScreeningRepository;
    private ScreeningDao screeningDao;
    private MovieDao movieDao;
    private RoomDao roomDao;
    private MovieRepository movieRepository;
    private RoomRepository roomRepository;
    private DateConverter dateConverter = new DateConverter();

    private final Movie movie = new Movie("TestMT1", "TestMG1", 60);
    private final Room room = new Room("TestRN1", 1, 1);
    private final MovieProjection movieProjection = new MovieProjection(
            null, movie.getMovieTitle(), movie.getMovieGenre(), movie.getMovieLength());
    private final RoomProjection roomProjection = new RoomProjection(
            null, room.getRoomName(), room.getSeatRows(), room.getSeatColumns());
    private final Date screeningTime = dateConverter.convertStringToDate("2000-01-02 00:00");
    private final Date screeningTime2 = dateConverter.convertStringToDate("2000-01-02 01:00");
    private final ScreeningCompositeKey screeningCompositeKey = new ScreeningCompositeKey(
            movieProjection, roomProjection, screeningTime);
    private final ScreeningProjection screeningProjection = new ScreeningProjection(screeningCompositeKey);
    private final Screening screening= new Screening(movie, room, screeningTime);

    JpaScreeningRepositoryTest() throws Exception {
    }


    @BeforeEach
    void setUp() {
        screeningDao = Mockito.mock(ScreeningDao.class);
        movieDao = Mockito.mock(MovieDao.class);
        roomDao = Mockito.mock(RoomDao.class);
        movieRepository = new JpaMovieRepository(movieDao);
        roomRepository = new JpaRoomRepository(roomDao);
        jpaScreeningRepository = new JpaScreeningRepository(
                screeningDao, movieDao, roomDao, movieRepository, roomRepository);
    }

    @Test
    void testCreateScreeningCreatesScreeningSuccessfully() throws Exception {
        // Given
        Mockito.when(movieDao.findByMovieTitle(movie.getMovieTitle()))
                .thenReturn(Optional.of(movieProjection));
        Mockito.when(roomDao.findByRoomName(room.getRoomName()))
                .thenReturn(Optional.of(roomProjection));
        Mockito.when(screeningDao
                .findByScreeningCompositeKey_MovieProjection_MovieTitleAndScreeningCompositeKey_RoomProjection_RoomNameAndScreeningCompositeKey_ScreeningTime(
                        movieProjection.getMovieTitle(), roomProjection.getRoomName(), screeningTime))
                .thenReturn(Optional.empty());

        // When
        jpaScreeningRepository.createScreening(movie.getMovieTitle(), room.getRoomName(), screeningTime);

        // Then
        Mockito.verify(screeningDao).save(screeningProjection);

    }

    @Test
    void testGetAllScreeningsShouldReturnSuccessfully() {
        // Given
        ArrayList<Screening> expectedResult = new ArrayList<Screening>(List.of(new Screening(
                movie, room, screeningTime)));
        Mockito.when(screeningDao.findAll()).thenReturn(List.of(screeningProjection));

        // When
        ArrayList<Screening> actualResult = jpaScreeningRepository.getAllScreenings();

        //Then
        Assertions.assertEquals(expectedResult, actualResult);
        Mockito.verify(screeningDao).findAll();
    }

    @Test
    void testCreateScreeningThrowsExceptionWhenMovieDoesNotExists() throws Exception {
        // Given
        Mockito.when(movieDao.findByMovieTitle(movie.getMovieTitle()))
                .thenReturn(Optional.empty());
        Mockito.when(roomDao.findByRoomName(room.getRoomName()))
                .thenReturn(Optional.of(roomProjection));
        Mockito.when(screeningDao
                .findByScreeningCompositeKey_MovieProjection_MovieTitleAndScreeningCompositeKey_RoomProjection_RoomNameAndScreeningCompositeKey_ScreeningTime(
                        movieProjection.getMovieTitle(), roomProjection.getRoomName(), screeningTime))
                .thenReturn(Optional.empty());

        // When
        Assertions.assertThrows(Exception.class, () -> jpaScreeningRepository.createScreening(movie.getMovieTitle(), room.getRoomName(), screeningTime));

        // Then
        Mockito.verify(screeningDao, Mockito.times(0)).save(screeningProjection);

    }

    @Test
    void testCreateScreeningThrowsExceptionWhenRoomDoesNotExists() throws Exception {
        // Given
        Mockito.when(movieDao.findByMovieTitle(movie.getMovieTitle()))
                .thenReturn(Optional.of(movieProjection));
        Mockito.when(roomDao.findByRoomName(room.getRoomName()))
                .thenReturn(Optional.empty());
        Mockito.when(screeningDao
                .findByScreeningCompositeKey_MovieProjection_MovieTitleAndScreeningCompositeKey_RoomProjection_RoomNameAndScreeningCompositeKey_ScreeningTime(
                        movieProjection.getMovieTitle(), roomProjection.getRoomName(), screeningTime))
                .thenReturn(Optional.empty());

        // When
        Assertions.assertThrows(Exception.class, () -> jpaScreeningRepository.createScreening(movie.getMovieTitle(), room.getRoomName(), screeningTime));

        // Then
        Mockito.verify(screeningDao, Mockito.times(0)).save(screeningProjection);

    }

    @Test
    void testCreateScreeningThrowsExceptionWhenScreeningAlreadyExists() throws Exception {
        // Given
        Mockito.when(movieDao.findByMovieTitle(movie.getMovieTitle()))
                .thenReturn(Optional.of(movieProjection));
        Mockito.when(roomDao.findByRoomName(room.getRoomName()))
                .thenReturn(Optional.of(roomProjection));
        Mockito.when(screeningDao
                .findByScreeningCompositeKey_MovieProjection_MovieTitleAndScreeningCompositeKey_RoomProjection_RoomNameAndScreeningCompositeKey_ScreeningTime(
                        movieProjection.getMovieTitle(), roomProjection.getRoomName(), screeningTime))
                .thenReturn(Optional.of(screeningProjection));

        // When
        Assertions.assertThrows(Exception.class, () -> jpaScreeningRepository.createScreening(movie.getMovieTitle(), room.getRoomName(), screeningTime));

        // Then
        Mockito.verify(screeningDao, Mockito.times(0)).save(screeningProjection);

    }

    @Test
    void testCreateScreeningThrowsExceptionWhenCanCreateThrowsException() throws Exception {
        // Given
        Mockito.when(movieDao.findByMovieTitle(movie.getMovieTitle()))
                .thenReturn(Optional.of(movieProjection));
        Mockito.when(roomDao.findByRoomName(room.getRoomName()))
                .thenReturn(Optional.of(roomProjection));
        Mockito.when(screeningDao
                .findByScreeningCompositeKey_MovieProjection_MovieTitleAndScreeningCompositeKey_RoomProjection_RoomNameAndScreeningCompositeKey_ScreeningTime(
                        movieProjection.getMovieTitle(), roomProjection.getRoomName(), screeningTime))
                .thenReturn(Optional.empty());
        Mockito.when(screeningDao.findAll()).thenReturn(
                new ArrayList<>(List.of(screeningProjection, new ScreeningProjection(
                        new ScreeningCompositeKey(movieProjection, roomProjection, screeningTime2)))));

        // When
        Assertions.assertThrows(Exception.class, () -> jpaScreeningRepository.createScreening(movie.getMovieTitle(), room.getRoomName(), screeningTime));

        // Then
        Mockito.verify(screeningDao, Mockito.times(0)).save(screeningProjection);

    }

    @Test
    void deleteScreeningDeletesScreeningSuccessfully() throws Exception {
        // Given
        Mockito.when(screeningDao.findByScreeningCompositeKey_MovieProjection_MovieTitleAndScreeningCompositeKey_RoomProjection_RoomNameAndScreeningCompositeKey_ScreeningTime(
                movie.getMovieTitle(), room.getRoomName(), screeningTime))
                .thenReturn(Optional.of(screeningProjection));
        // When
        jpaScreeningRepository.deleteScreening(movie.getMovieTitle(), room.getRoomName(), screeningTime);

        // Then
        Mockito.verify(screeningDao, Mockito.times(1))
                .deleteByScreeningCompositeKey_MovieProjection_MovieTitleAndScreeningCompositeKey_RoomProjection_RoomNameAndScreeningCompositeKey_ScreeningTime(
                        movieProjection.getMovieTitle(), roomProjection.getRoomName(), screeningTime);
    }

    @Test
    void deleteScreeningThrowsExceptionWhenScreeningDoesNotExists() throws Exception {
        // Given
        Mockito.when(screeningDao.findByScreeningCompositeKey_MovieProjection_MovieTitleAndScreeningCompositeKey_RoomProjection_RoomNameAndScreeningCompositeKey_ScreeningTime(
                movie.getMovieTitle(), room.getRoomName(), screeningTime))
                .thenReturn(Optional.empty());
        // When
        Assertions.assertThrows(Exception.class, () -> jpaScreeningRepository.deleteScreening(movie.getMovieTitle(), room.getRoomName(), screeningTime));

        // Then
        Mockito.verify(screeningDao, Mockito.times(0)).delete(screeningProjection);
    }
}