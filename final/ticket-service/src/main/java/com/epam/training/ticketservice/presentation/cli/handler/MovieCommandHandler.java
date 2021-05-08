package com.epam.training.ticketservice.presentation.cli.handler;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.ArrayList;

@ShellComponent
public class MovieCommandHandler {

    @ShellMethod(value = "Creates a new movie", key = "create movie")
    public String createMovie(String movieTitle, String movieGenre, int movieLength){
        return "Created a new movie: \""+ movieTitle +"\"!";
    }

    @ShellMethod(value = "Lists all movies", key = "list movies")
    public ArrayList<String> listMovies(){
        ArrayList<String> movies = new ArrayList<>();

        if(movies.isEmpty()) {
            System.out.println("There are no movies at the moment!");
        }

        return movies;
    }

    @ShellMethod(value = "Updates an existing movie", key = "update movie")
    public String updateMovie(String movieTitle, String movieGenre, int movieLength){
        return "Updated the movie: \""+ movieTitle +"\"!";
    }

    @ShellMethod(value = "Deletes an existing movie", key = "delete movie")
    public String deleteMovie(String movieTitle){
        return "Deleted the movie: \""+ movieTitle +"\"!";
    }
}
