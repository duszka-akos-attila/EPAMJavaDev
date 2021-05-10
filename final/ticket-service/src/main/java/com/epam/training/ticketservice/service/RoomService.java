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

    public void createRoom(String roomName, int seatRows, int seatColumns) {
        roomRepository.createRoom(new Room(
                roomName, seatRows, seatColumns
        ));
    }

    public void listRooms() {
        ArrayList<Room> rooms = roomRepository.getAllRooms();
        if(rooms.isEmpty()) {
            System.out.println("There are no rooms at the moment");
        }
        else {
            for (Room room : rooms) {
                System.out.println(room.toString());
            }
        }
    }

    public void updateRoom(String roomName, int seatRows, int seatColumns) throws Exception {
        roomRepository.updateRoom(new Room(
                roomName, seatRows, seatColumns
        ));
    }

    public void deleteRoom(String roomName) {
        roomRepository.deleteRoom(roomName);
    }
}
