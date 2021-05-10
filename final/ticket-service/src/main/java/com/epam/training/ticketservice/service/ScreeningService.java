package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    public ScreeningService(ScreeningRepository screeningRepository, MovieRepository movieRepository, RoomRepository roomRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    public void createScreening(String movieTitle, String roomName, Date screeningTime) throws Exception {
        screeningRepository.createScreening(new Screening(
                movieRepository.findMovieByTitle(movieTitle),
                roomRepository.findRoomByRoomName(roomName),
                screeningTime
        ));
    }

    public void listScreenings() {
        ArrayList<Screening> screenings = screeningRepository.getAllScreenings();
        if(screenings.isEmpty()) {
            System.out.println("There are no screenings at the moment");
        }
        else {
            for (Screening screening : screenings) {
                System.out.println(screening.toString());
            }
        }
    }

    public void deleteScreening(String movieTitle, String roomName, Date screeningTime) throws Exception {
        screeningRepository.deleteScreening(movieTitle, roomName, screeningTime);
    }
}
