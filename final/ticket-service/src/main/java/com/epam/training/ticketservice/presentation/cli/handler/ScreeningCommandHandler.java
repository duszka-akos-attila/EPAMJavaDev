package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.security.command.PrivilegedCommand;
import com.epam.training.ticketservice.security.session.SessionManager;
import com.epam.training.ticketservice.security.session.TokenCollector;
import com.epam.training.ticketservice.service.ScreeningService;
import com.epam.training.ticketservice.utilities.converter.DateConverter;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.ArrayList;
import java.util.Date;

@ShellComponent
public class ScreeningCommandHandler extends PrivilegedCommand {

    private final ScreeningService screeningService;
    private final DateConverter dateConverter;

    public ScreeningCommandHandler(
            SessionManager sessionManager,
            TokenCollector tokenCollector,
            ScreeningService screeningService,
            DateConverter dateConverter) {

        super(sessionManager, tokenCollector);
        this.screeningService = screeningService;
        this.dateConverter = dateConverter;
    }

    @ShellMethod(value = "Creates a new screening", key = "create screening")
    @ShellMethodAvailability("isUserPrivileged")
    public String createScreening(String movieTitle, String roomName, String screeningTime) {
        try {
            screeningService.createScreening(movieTitle, roomName, dateConverter.convertStringToDate(screeningTime));
        } catch (Exception e) {
            return e.getMessage();
        }

        // return "Created a new screening for: \"" + movieTitle + "\" at \"" + roomName + "\"!";
        return null;
    }

    @ShellMethod(value = "Lists all screenings", key = "list screenings")
    public String listScreenings() {
        StringBuilder screeningList = new StringBuilder();
        ArrayList<Screening> screenings = screeningService.getAllScreenings();
        if (screenings.isEmpty()) {
            return "There are no screenings";
        } else {
            for (Screening screening : screenings) {
                screeningList.append(screening.toString()).append("\n");
            }
        }
        return screeningList.toString();
    }

    @ShellMethod(value = "Deletes an existing screening", key = "delete screening")
    @ShellMethodAvailability("isUserPrivileged")
    public String deleteScreening(String movieTitle, String roomName, String screeningTime) {
        try {
            screeningService.deleteScreening(movieTitle,roomName,dateConverter.convertStringToDate(screeningTime));
        } catch (Exception e) {
            return e.getMessage();
        }
        // return "Deleted the the screening: \"" + movieTitle + "\" at \"" + roomName + "\"!";
        return null;
    }
}