package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.security.command.PrivilegedCommand;
import com.epam.training.ticketservice.security.session.SessionManager;
import com.epam.training.ticketservice.security.session.TokenCollector;
import com.epam.training.ticketservice.service.MovieService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.ArrayList;

@ShellComponent
public class MovieCommandHandler extends PrivilegedCommand {

    private final MovieService movieService;

    public MovieCommandHandler(
            SessionManager sessionManager, TokenCollector tokenCollector, MovieService movieService) {
        super(sessionManager, tokenCollector);
        this.movieService = movieService;
    }

    @ShellMethod(value = "Creates a new movie", key = "create movie")
    @ShellMethodAvailability("isUserPrivileged")
    public String createMovie(String movieTitle, String movieGenre, int movieLength) {
        try {
            movieService.createMovie(movieTitle,movieGenre,movieLength);
        } catch (Exception e) {
            return e.getMessage();
        }

        // return "Movie " + movieTitle + " is successfully created!";
        return null;
    }

    @ShellMethod(value = "Lists all movies", key = "list movies")
    public String listMovies() {
        StringBuilder movieList = new StringBuilder();
        ArrayList<Movie> movies = movieService.getAllMovies();
        if (movies.isEmpty()) {
            return "There are no movies at the moment";
        } else {
            for (Movie movie : movies) {
                movieList.append(movie.toString()).append("\n");
            }
        }
        return movieList.toString();
    }

    @ShellMethod(value = "Updates an existing movie", key = "update movie")
    @ShellMethodAvailability("isUserPrivileged")
    public String updateMovie(String movieTitle, String movieGenre, int movieLength) {
        try {
            movieService.updateMovie(movieTitle,movieGenre,movieLength);
        } catch (Exception e) {
            return e.getMessage();
        }

        // return "Updated the movie: \"" + movieTitle + "\"!";
        return null;
    }

    @ShellMethod(value = "Deletes an existing movie", key = "delete movie")
    @ShellMethodAvailability("isUserPrivileged")
    public String deleteMovie(String movieTitle) {
        try {
            movieService.deleteMovie(movieTitle);
        } catch (Exception e) {
            return e.getMessage();
        }

        // return "Deleted the movie: \"" + movieTitle + "\"!";
        return null;
    }
}
