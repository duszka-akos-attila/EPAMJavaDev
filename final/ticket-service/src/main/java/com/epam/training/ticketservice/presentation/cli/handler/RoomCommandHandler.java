package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.security.command.PrivilegedCommand;
import com.epam.training.ticketservice.security.session.SessionManager;
import com.epam.training.ticketservice.security.session.TokenCollector;
import com.epam.training.ticketservice.service.RoomService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.ArrayList;

@ShellComponent
public class RoomCommandHandler extends PrivilegedCommand {

    private final RoomService roomService;

    public RoomCommandHandler(SessionManager sessionManager, TokenCollector tokenCollector, RoomService roomService) {
        super(sessionManager, tokenCollector);
        this.roomService = roomService;
    }

    @ShellMethod(value = "Creates a new room", key = "create room")
    @ShellMethodAvailability("isUserPrivileged")
    public String createRoom(String roomName, int seatRows, int seatColumns) {
        try {
            roomService.createRoom(roomName,seatRows,seatColumns);
        } catch (Exception e) {
            return e.getMessage();
        }

        // return "Created a new room: \"" + roomName + "\"!";
        return null;
    }

    @ShellMethod(value = "Lists all rooms", key = "list rooms")
    public String listRooms() {
        StringBuilder roomList = new StringBuilder();
        ArrayList<Room> rooms = roomService.getAllRooms();
        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        } else {
            for (Room room : rooms) {
                roomList.append(room.toString()).append("\n");
            }
        }
        return roomList.toString();
    }

    @ShellMethod(value = "Updates an existing room", key = "update room")
    @ShellMethodAvailability("isUserPrivileged")
    public String updateRoom(String roomName, int seatRows, int seatColumns) {
        try {
            roomService.updateRoom(roomName, seatRows, seatColumns);
        } catch (Exception e) {
            return "Room '" + roomName + "' not found!";
        }

        // return "Updated the room: \"" + roomName + "\"!";
        return null;
    }

    @ShellMethod(value = "Deletes an existing room", key = "delete room")
    @ShellMethodAvailability("isUserPrivileged")
    public String deleteRoom(String roomName) {
        try {
            roomService.deleteRoom(roomName);
        } catch (Exception e) {
            return e.getMessage();
        }

        // return "Deleted the room: \"" + roomName + "\"!";
        return null;
    }
}