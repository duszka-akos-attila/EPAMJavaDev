package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Room;

import java.util.ArrayList;

public interface RoomRepository {

    void createRoom(Room room) throws Exception;

    ArrayList<Room> getAllRooms();

    Room findRoomByRoomName(String roomName) throws Exception;

    void updateRoomByRoom(Room room) throws Exception;

    void deleteRoomByRoomName(String roomName) throws Exception;
}
