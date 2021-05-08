package com.epam.training.ticketservice.presentation.cli.handler;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class MovieCommandHandler {

    @ShellMethod(value = "Creates a new movie", key = "create movie")
    public String createMovie(String movieTitle, String movieGenre, int movieLength){
        return "Created a new movie: \""+ movieTitle +"\"!";
    }
}
