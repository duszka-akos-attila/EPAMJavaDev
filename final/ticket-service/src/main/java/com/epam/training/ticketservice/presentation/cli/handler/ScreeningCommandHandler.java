package com.epam.training.ticketservice.presentation.cli.handler;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.ArrayList;
import java.util.Date;

@ShellComponent
public class ScreeningCommandHandler {

    @ShellMethod(value = "Creates a new screening", key = "create screening")
    public String createScreening(String movieTitle, String roomName, Date screeningTime){
        return "Created a new screening for: \""+ movieTitle +"\" at \""+ roomName +"\"!";
    }

    @ShellMethod(value = "Lists all screenings", key = "list screenings")
    public ArrayList<String> listScreenings(){
        ArrayList<String> screenings = new ArrayList<>();

        if(screenings.isEmpty()) {
            System.out.println("There are no screenings at the moment!");
        }

        return screenings;
    }

    @ShellMethod(value = "Updates an existing screening", key = "update screening")
    public String updateScreening(String movieTitle, String roomName, Date screeningTime){
        return "Updated the screening: \""+ movieTitle +"\" at \""+ roomName +"\"!";
    }

    @ShellMethod(value = "Deletes an existing screening", key = "delete screening")
    public String deleteScreening(String movieTitle, String roomName){
        return "Deleted the the screening: \""+ movieTitle +"\" at \""+ roomName +"\"!";
    }
}