package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Repository
public class JpaRoomRepository implements RoomRepository {

    private final RoomDao roomDao;

    public JpaRoomRepository(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    @Override
    public void createRoom(Room room) throws Exception {
        if (roomDao.findByRoomName(room.getRoomName()).isEmpty()) {
            roomDao.save(new RoomProjection(
                    null,
                    room.getRoomName(),
                    room.getSeatRows(),
                    room.getSeatColumns()
            ));
        } else {
            throw new Exception("Room with title '" + room.getRoomName() + "' is already exists!");
        }
    }

    @Override
    public ArrayList<Room> getAllRooms() {
        return roomDao.findAll().stream()
                .map(roomProjection -> new Room(
                        roomProjection.getRoomName(),
                        roomProjection.getSeatRows(),
                        roomProjection.getSeatColumns()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Room findRoomByRoomName(String roomName) throws Exception {
        RoomProjection roomProjection = roomDao.findByRoomName(roomName).orElseThrow(
            () -> new Exception("Room not found with '" + roomName + "' name!")
        );
        return new Room(
                roomProjection.getRoomName(),
                roomProjection.getSeatRows(),
                roomProjection.getSeatColumns());
    }

    @Override
    public void updateRoomByRoom(Room room) throws Exception {
        RoomProjection roomProjection = roomDao.findByRoomName(room.getRoomName()).orElseThrow(
            () -> new Exception("Room not found with \"" + room.getRoomName() + "\" name!")
        );
        roomProjection.setSeatRows(room.getSeatRows());
        roomProjection.setSeatColumns(room.getSeatColumns());
        roomDao.save(roomProjection);

    }

    @Override
    @Transactional
    public void deleteRoomByRoomName(String roomName) throws Exception {
        if (roomDao.findByRoomName(roomName).isEmpty()) {
            throw new Exception("Room not found with '" + roomName + "' name!");
        } else {
            roomDao.deleteByRoomName(roomName);
        }
    }
}
