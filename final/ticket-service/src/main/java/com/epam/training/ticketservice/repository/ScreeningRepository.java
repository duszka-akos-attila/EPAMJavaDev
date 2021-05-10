package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Screening;

import java.util.ArrayList;
import java.util.Date;

public interface ScreeningRepository {

    void createScreening(Screening screening) throws Exception;

    ArrayList<Screening> getAllScreenings();

    void updateScreening(String movieTitle ,String roomName, Date screeningTime, Screening screening) throws Exception;

    void deleteScreening(String movieTitle ,String roomName, Date screeningTime) throws Exception;
}
