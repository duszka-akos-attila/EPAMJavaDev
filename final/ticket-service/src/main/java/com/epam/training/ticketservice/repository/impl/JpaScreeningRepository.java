package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.dao.ScreeningDao;
import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import com.epam.training.ticketservice.dataaccess.projection.ScreeningProjection;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaScreeningRepository implements ScreeningRepository {

    //TODO Create a MovieProjection and RoomProjection factory, to get rid of duplications!

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
        MovieProjection movieProjection = movieDao.findByMovieTitle(screening.getMovieTitle())
                .orElseThrow(() -> new Exception("Movie not found with \""+ screening.getMovieTitle() +"\" title!"));

        RoomProjection roomProjection = roomDao.findByRoomName(screening.getRoomName())
                .orElseThrow(() -> new Exception("Room not found with \""+ screening.getRoomName() +"\" name!"));

        screeningDao.save(new ScreeningProjection(
                UUID.nameUUIDFromBytes(screening.getMovieTitle().getBytes()),
                movieProjection,
                roomProjection,
                screening.getScreeningTime()));
    }

    @Override
    public ArrayList<Screening> getAllScreenings() {
        return screeningDao.findAll().stream()
                .map(screeningProjection -> new Screening(
                        screeningProjection.getMovieProjection().getMovieTitle(),
                        screeningProjection.getRoomProjection().getRoomName(),
                        screeningProjection.getScreeningTime()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void updateScreening(String movieTitle, String roomName, Date screeningTime, Screening screening) throws Exception {
        MovieProjection oldMovieProjection = movieDao.findByMovieTitle(movieTitle)
                .orElseThrow(() -> new Exception("Movie not found with \""+ movieTitle +"\" title!"));

        MovieProjection newMovieProjection = movieDao.findByMovieTitle(screening.getMovieTitle())
                .orElseThrow(() -> new Exception("Movie not found with \""+ screening.getMovieTitle() +"\" title!"));

        RoomProjection oldRoomProjection = roomDao.findByRoomName(roomName)
                .orElseThrow(() -> new Exception("Room not found with \""+ roomName +"\" name!"));

        RoomProjection newRoomProjection = roomDao.findByRoomName(screening.getRoomName())
                .orElseThrow(() -> new Exception("Room not found with \""+ screening.getRoomName() +"\" name!"));

        ScreeningProjection screeningProjection = screeningDao.findByMovieProjectionAndRoomProjectionAndScreeningTime(
                oldMovieProjection, oldRoomProjection, screeningTime)
                .orElseThrow(() -> new Exception("Screening not found with the given parameters: Movie: " + movieTitle + ", Room: " + roomName + "Screening at: " + screeningTime));

        screeningDao.delete(screeningProjection);
        screeningDao.save(new ScreeningProjection(
                UUID.nameUUIDFromBytes(screening.getMovieTitle().getBytes()),
                newMovieProjection,
                newRoomProjection,
                screening.getScreeningTime()));
    }

    @Override
    public void deleteScreening(String movieTitle, String roomName, Date screeningTime) throws Exception {
        MovieProjection movieProjection = movieDao.findByMovieTitle(movieTitle)
                .orElseThrow(() -> new Exception("Movie not found with \""+ movieTitle +"\" title!"));

        RoomProjection roomProjection = roomDao.findByRoomName(roomName)
                .orElseThrow(() -> new Exception("Room not found with \""+ roomName +"\" name!"));

        screeningDao.deleteByMovieProjectionAndRoomProjectionAndScreeningTime(movieProjection, roomProjection, screeningTime);
    }
}
