package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.security.command.PrivilegedCommand;
import com.epam.training.ticketservice.security.service.AuthenticationService;
import com.epam.training.ticketservice.security.session.SessionManager;
import com.epam.training.ticketservice.security.session.TokenCollector;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class AuthenticationCommandHandler extends PrivilegedCommand {

    private final AuthenticationService authenticationService;
    private final SessionManager sessionManager;
    private final TokenCollector tokenCollector;

    public AuthenticationCommandHandler(AuthenticationService authenticationService, SessionManager sessionManager, TokenCollector tokenCollector) {
        super(sessionManager, tokenCollector);
        this.authenticationService = authenticationService;
        this.sessionManager = sessionManager;
        this.tokenCollector = tokenCollector;
    }


    @ShellMethodAvailability("isUserNotSignedIn")
    @ShellMethod(value = "Signing into a privileged account", key = "sign in privileged")
    public String signInPrivileged(String privilegedUserName, String privilegedUserPassword){
        String result = null;
        switch (authenticationService.signIn(privilegedUserName, privilegedUserPassword)) {
            case 0:
                result = "Welcome "+ privilegedUserName +"!";
                break;

            case -1:
                result = "Incorrect Username!";
                break;

            case -2:
                result = "Incorrect Password!";
                break;

            case -3:
                result = "User is already signed in!";
                break;
        }
        return "Sign "+ privilegedUserName +"!";
    }

    @ShellMethod(value = "Signing out from an account", key = "sign out")
    @ShellMethodAvailability("isUserSignedIn")
    public String signOut(){
        authenticationService.signOut();
        tokenCollector.removeToken(tokenCollector.getTokens().get(tokenCollector.getTokens().size()-1));
        return "You are now signed out!";
    }

    @ShellMethod(value = "Describes a logged in account", key = "describe account")
    @ShellMethodAvailability("isUserSignedIn")
    public String describeAccount(){
        String userName = "admin";
        if (userName != null) {
            return "Signed in with privileged account '" + userName + "'";
        }

        return "You are not signed in!";
    }
}