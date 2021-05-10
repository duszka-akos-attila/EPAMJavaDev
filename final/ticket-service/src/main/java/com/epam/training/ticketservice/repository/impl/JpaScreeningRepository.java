package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.dao.ScreeningDao;
import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import com.epam.training.ticketservice.dataaccess.projection.ScreeningProjection;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaScreeningRepository implements ScreeningRepository {


    private final ScreeningDao screeningDao;
    private final MovieDao movieDao;
    private final RoomDao roomDao;

    public JpaScreeningRepository(ScreeningDao screeningDao, MovieDao movieDao, RoomDao roomDao) {
        this.screeningDao = screeningDao;
        this.movieDao = movieDao;
        this.roomDao = roomDao;
    }

    @Override
    public void createScreening(Screening screening) throws Exception {
        screeningDao.save(new ScreeningProjection(
                UUID.nameUUIDFromBytes(screening.getMovie().getMovieTitle().getBytes()),
                findMovieByTitle(screening.getMovie().getMovieTitle()),
                findRoomByName(screening.getRoom().getRoomName()),
                screening.getScreeningTime()));
    }

    @Override
    public ArrayList<Screening> getAllScreenings() {
        return screeningDao.findAll().stream()
                .map(screeningProjection -> new Screening(
                        MovieProjectionToMovie(screeningProjection.getMovieProjection()),
                        RoomProjectionToRoom(screeningProjection.getRoomProjection()),
                        screeningProjection.getScreeningTime()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void deleteScreening(String movieTitle, String roomName, Date screeningTime) throws Exception {
        screeningDao.deleteByMovieProjectionAndRoomProjectionAndScreeningTime(
                findMovieByTitle(movieTitle), findRoomByName(roomName), screeningTime);
    }

    /*
        Assistance Methods below
    */

    private Movie MovieProjectionToMovie(MovieProjection movieProjection) {
        return new Movie(
                movieProjection.getMovieTitle(),
                movieProjection.getMovieGenre(),
                movieProjection.getMovieLength()
        );
    }

    private Room RoomProjectionToRoom(RoomProjection roomProjection) {
        return new Room(
                roomProjection.getRoomName(),
                roomProjection.getSeatRows(),
                roomProjection.getSeatColumns()
        );
    }

    private MovieProjection findMovieByTitle(String movieTitle) throws Exception {
        return movieDao.findByMovieTitle(movieTitle)
                .orElseThrow(() -> new Exception("Movie not found with \""+ movieTitle +"\" title!"));
    }

    private RoomProjection findRoomByName(String roomName) throws Exception {
        return roomDao.findByRoomName(roomName)
                .orElseThrow(() -> new Exception("Room not found with \""+ roomName +"\" name!"));
    }
}
