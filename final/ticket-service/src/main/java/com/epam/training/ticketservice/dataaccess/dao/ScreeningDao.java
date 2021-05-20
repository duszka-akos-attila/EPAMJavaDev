package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.projection.ScreeningProjection;
import com.epam.training.ticketservice.dataaccess.projection.compositekey.ScreeningCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface ScreeningDao extends JpaRepository<ScreeningProjection, ScreeningCompositeKey> {

    Optional<ScreeningProjection> findByScreeningCompositeKey_MovieProjection_MovieTitleAndScreeningCompositeKey_RoomProjection_RoomNameAndScreeningCompositeKey_ScreeningTime(
            String movieTitle, String roomName, Date screeningTime);

    void deleteByScreeningCompositeKey_MovieProjection_MovieTitleAndScreeningCompositeKey_RoomProjection_RoomNameAndScreeningCompositeKey_ScreeningTime(
            String movieTitle, String roomName, Date screeningTime);
}
