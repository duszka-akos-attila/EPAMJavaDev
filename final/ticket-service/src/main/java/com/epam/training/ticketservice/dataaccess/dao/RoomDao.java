package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface RoomDao extends JpaRepository<RoomProjection, Long> {

    Optional<RoomProjection> findByRoomName(String roomName);

    void deleteByRoomName(String roomName);
}
