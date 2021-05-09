package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Screening;

import java.util.ArrayList;
import java.util.Date;

public interface ScreeningRepository {

    void createScreening(Screening screening);

    ArrayList<Screening> getAllScreenings();

    void updateScreening(String roomName, Date screeningTime, Screening screening);

    void deleteScreening(String roomName, Date screeningTime);
}
