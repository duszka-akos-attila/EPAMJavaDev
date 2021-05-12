package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void createRoom(String roomName, int seatRows, int seatColumns) throws Exception {
        roomRepository.createRoom(new Room(
                roomName, seatRows, seatColumns
        ));
    }

    public ArrayList<Room> getAllRooms() {
        return roomRepository.getAllRooms();
    }

    public void updateRoom(String roomName, int seatRows, int seatColumns) throws Exception {
        roomRepository.updateRoomByRoom(new Room(
                roomName, seatRows, seatColumns
        ));
    }

    public void deleteRoom(String roomName) throws Exception {
        roomRepository.deleteRoomByRoomName(roomName);
    }
}
