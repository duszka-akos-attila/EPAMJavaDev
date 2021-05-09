package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Room;

import java.util.ArrayList;

public interface RoomRepository {

    void createRoom(Room room);

    ArrayList<Room> getAllRooms();

    Room findRoomByRoomName(String roomName);

    void updateRoom(String roomName, Room room);

    void deleteRoom(String roomName);
}
