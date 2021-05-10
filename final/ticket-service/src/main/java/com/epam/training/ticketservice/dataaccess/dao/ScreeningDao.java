package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import com.epam.training.ticketservice.dataaccess.projection.ScreeningProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface ScreeningDao extends JpaRepository<ScreeningProjection, UUID> {

    Optional<ScreeningProjection> findByMovieProjectionAndRoomProjectionAndScreeningTime(MovieProjection movieProjection, RoomProjection roomProjection, Date screeningTime);

    void deleteByMovieProjectionAndRoomProjectionAndScreeningTime(MovieProjection movieProjection, RoomProjection roomProjection, Date screeningTime);
}
