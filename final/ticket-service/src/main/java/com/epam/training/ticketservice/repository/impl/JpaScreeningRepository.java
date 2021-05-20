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
import com.epam.training.ticketservice.repository.ScreeningRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

@Repository
public class JpaScreeningRepository implements ScreeningRepository {


    private final ScreeningDao screeningDao;
    private final MovieDao movieDao;
    private final RoomDao roomDao;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    public JpaScreeningRepository(ScreeningDao screeningDao, MovieDao movieDao, RoomDao roomDao, MovieRepository movieRepository, RoomRepository roomRepository) {
        this.screeningDao = screeningDao;
        this.movieDao = movieDao;
        this.roomDao = roomDao;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void createScreening(String movieTitle, String roomName, Date screeningTime) throws Exception {
        MovieProjection movieProjection = movieDao.findByMovieTitle(movieTitle).orElseThrow(
                        () -> new Exception("Movie not found with '"+ movieTitle +"' title!"));

        RoomProjection roomProjection = roomDao.findByRoomName(roomName).orElseThrow(
                () -> new Exception("Room not found with \""+ roomName +"\" name!"));

        if(screeningDao.findByScreeningCompositeKey_MovieProjection_MovieTitleAndScreeningCompositeKey_RoomProjection_RoomNameAndScreeningCompositeKey_ScreeningTime(
                movieTitle,
                roomName,
                screeningTime).isEmpty()) {
            System.out.println("Screening not found, creating...");
            screeningDao.save(new ScreeningProjection(
                    new ScreeningCompositeKey(
                    movieProjection, roomProjection, screeningTime
                    )));
            System.out.println("Creation done!");
        }
        else {
            throw new Exception("Screening with the given parameters is already exists!");
        }
    }

    @Override
    public ArrayList<Screening> getAllScreenings() {
        return screeningDao.findAll().stream()
                .map(screeningProjection -> new Screening(
                        MovieProjectionToMovie(screeningProjection.getScreeningCompositeKey().getMovieProjection()),
                        RoomProjectionToRoom(screeningProjection.getScreeningCompositeKey().getRoomProjection()),
                        screeningProjection.getScreeningCompositeKey().getScreeningTime()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    public void deleteScreening(String movieTitle, String roomName, Date screeningTime) throws Exception {
        if(screeningDao.findByScreeningCompositeKey_MovieProjection_MovieTitleAndScreeningCompositeKey_RoomProjection_RoomNameAndScreeningCompositeKey_ScreeningTime(
                movieTitle, roomName, screeningTime).isEmpty()) {
            throw new Exception("Screening not found with the given parameters!");
        }
        else {
            screeningDao.deleteByScreeningCompositeKey_MovieProjection_MovieTitleAndScreeningCompositeKey_RoomProjection_RoomNameAndScreeningCompositeKey_ScreeningTime(
                    movieTitle, roomName, screeningTime);
        }
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
