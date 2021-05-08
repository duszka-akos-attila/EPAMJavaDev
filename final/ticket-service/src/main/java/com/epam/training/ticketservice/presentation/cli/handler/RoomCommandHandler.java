package com.epam.training.ticketservice.presentation.cli.handler;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.ArrayList;

@ShellComponent
public class RoomCommandHandler {

    @ShellMethod(value = "Creates a new room", key = "create room")
    public String createRoom(String roomName, int seatRows, int seatColumns){
        return "Created a new room: \""+ roomName +"\"!";
    }

    @ShellMethod(value = "Lists all rooms", key = "list rooms")
    public ArrayList<String> listRooms(){
        ArrayList<String> rooms = new ArrayList<>();

        if(rooms.isEmpty()) {
            System.out.println("There are no rooms at the moment!");
        }

        return rooms;
    }

    @ShellMethod(value = "Updates an existing room", key = "update room")
    public String updateRoom(String roomName, int seatRows, int seatColumns){
        return "Updated the room: \""+ roomName +"\"!";
    }

    @ShellMethod(value = "Deletes an existing room", key = "delete room")
    public String deleteRoom(String roomName){
        return "Deleted the room: \""+ roomName +"\"!";
    }
}