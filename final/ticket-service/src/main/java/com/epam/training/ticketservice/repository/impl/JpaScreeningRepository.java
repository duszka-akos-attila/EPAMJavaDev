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
import java.time.Duration;
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

    public JpaScreeningRepository(
            ScreeningDao screeningDao,
            MovieDao movieDao,
            RoomDao roomDao,
            MovieRepository movieRepository,
            RoomRepository roomRepository) {

        this.screeningDao = screeningDao;
        this.movieDao = movieDao;
        this.roomDao = roomDao;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void createScreening(String movieTitle, String roomName, Date screeningTime) throws Exception {
        MovieProjection movieProjection = movieDao.findByMovieTitle(movieTitle).orElseThrow(
            () -> new Exception("Movie not found with '" + movieTitle + "' title!"));

        RoomProjection roomProjection = roomDao.findByRoomName(roomName).orElseThrow(
            () -> new Exception("Room not found with \"" + roomName + "\" name!"));

        if (screeningDao.findByScreeningCompositeKey_MovieProjection_MovieTitleAndScreeningCompositeKey_RoomProjection_RoomNameAndScreeningCompositeKey_ScreeningTime(
                movieTitle,
                roomName,
                screeningTime).isEmpty()) {

            if (canCreateScreening(new Screening(
                    movieProjectionToMovie(movieProjection),
                    roomProjectionToRoom(roomProjection),
                    screeningTime), 10)) {

                screeningDao.save(new ScreeningProjection(
                        new ScreeningCompositeKey(
                                movieProjection, roomProjection, screeningTime)));
            }
        } else {
            throw new Exception("Screening with the given parameters is already exists!");
        }
    }

    @Override
    public ArrayList<Screening> getAllScreenings() {
        return screeningDao.findAll().stream()
                .map(screeningProjection -> new Screening(
                        movieProjectionToMovie(screeningProjection.getScreeningCompositeKey().getMovieProjection()),
                        roomProjectionToRoom(screeningProjection.getScreeningCompositeKey().getRoomProjection()),
                        screeningProjection.getScreeningCompositeKey().getScreeningTime()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    public void deleteScreening(String movieTitle, String roomName, Date screeningTime) throws Exception {
        if (screeningDao.findByScreeningCompositeKey_MovieProjection_MovieTitleAndScreeningCompositeKey_RoomProjection_RoomNameAndScreeningCompositeKey_ScreeningTime(
                movieTitle, roomName, screeningTime).isEmpty()) {
            throw new Exception("Screening not found with the given parameters!");
        } else {
            screeningDao.deleteByScreeningCompositeKey_MovieProjection_MovieTitleAndScreeningCompositeKey_RoomProjection_RoomNameAndScreeningCompositeKey_ScreeningTime(
                    movieTitle, roomName, screeningTime);
        }
    }

    /*
        Assistance Methods below
    */

    private Movie movieProjectionToMovie(MovieProjection movieProjection) {
        return new Movie(
                movieProjection.getMovieTitle(),
                movieProjection.getMovieGenre(),
                movieProjection.getMovieLength()
        );
    }

    private Room roomProjectionToRoom(RoomProjection roomProjection) {
        return new Room(
                roomProjection.getRoomName(),
                roomProjection.getSeatRows(),
                roomProjection.getSeatColumns()
        );
    }

    public Date[] getScreeningInterval(Screening screening) {
        Date screeningEnds = Date.from(screening.getScreeningTime().toInstant()
                .plus(Duration.ofMinutes(screening.getMovie().getMovieLength())));
        return new Date[]{screening.getScreeningTime(), screeningEnds};
    }

    public boolean canCreateScreening(Screening screening, int breakPeriodInMinutes) throws Exception {
        ArrayList<Screening> screenings = getAllScreenings();

        for (Screening screeningFromList : screenings) {
            Date[] screeningInterval = getScreeningInterval(screeningFromList);
            Date[] breakPeriod = getBreakPeriod(screeningFromList, breakPeriodInMinutes);

            if (screeningFromList.getRoom().getRoomName().equals(screening.getRoom().getRoomName())) {

                if ((getScreeningInterval(screening)[0].before(screeningInterval[1])
                        && getScreeningInterval(screening)[0].after(screeningInterval[0]))
                        || (getScreeningInterval(screening)[1].before(screeningInterval[1])
                        && getScreeningInterval(screening)[1].after(screeningInterval[0]))) {
                    throw new Exception("There is an overlapping screening");
                }

                if ((getScreeningInterval(screening)[0].equals(breakPeriod[0])
                        || getScreeningInterval(screening)[0].before(breakPeriod[1])
                        && getScreeningInterval(screening)[0].after(breakPeriod[0]))
                        || (getBreakPeriod(screening,breakPeriodInMinutes)[0].before(screeningInterval[1])
                        && getBreakPeriod(screening,breakPeriodInMinutes)[1].after(screeningInterval[0]))) {
                    throw new Exception("This would start in the break period after another screening in this room");
                }
            }
        }
        return true;
    }

    public Date[] getBreakPeriod(Screening screening, int breakPeriodInMinutes) {
        Date startOfBreak = getScreeningInterval(screening)[1];
        Date endOfBreak = Date.from(getScreeningInterval(screening)[1].toInstant()
                .plus(Duration.ofMinutes(breakPeriodInMinutes)));
        return new Date[]{startOfBreak, endOfBreak};
    }
}
