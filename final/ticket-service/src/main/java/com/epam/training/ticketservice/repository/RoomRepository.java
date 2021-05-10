package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Room;

import java.util.ArrayList;

public interface RoomRepository {

    void createRoom(Room room);

    ArrayList<Room> getAllRooms();

    Room findRoomByRoomName(String roomName) throws Exception;

    void updateRoom(Room room) throws Exception;

    void deleteRoom(String roomName);
}
