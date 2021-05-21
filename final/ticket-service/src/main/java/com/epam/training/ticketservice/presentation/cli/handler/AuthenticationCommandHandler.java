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

    public AuthenticationCommandHandler(
            AuthenticationService authenticationService, SessionManager sessionManager, TokenCollector tokenCollector) {
        super(sessionManager, tokenCollector);
        this.authenticationService = authenticationService;
        this.sessionManager = sessionManager;
        this.tokenCollector = tokenCollector;
    }


    @ShellMethodAvailability("isUserNotSignedIn")
    @ShellMethod(value = "Signing into a privileged account", key = "sign in privileged")
    public String signInPrivileged(String privilegedUserName, String privilegedUserPassword) {
        int result = authenticationService.signIn(privilegedUserName, privilegedUserPassword);

        if (result != 0) {
            return  "Login failed due to incorrect credentials";
        }
        return null;
    }

    @ShellMethod(value = "Signing out from an account", key = "sign out")
    @ShellMethodAvailability("isUserSignedIn")
    public String signOut() {
        authenticationService.signOut();
        tokenCollector.removeToken(tokenCollector.getTokens().get(tokenCollector.getTokens().size() - 1));
        return "You are now signed out!";
    }

    @ShellMethod(value = "Describes a logged in account", key = "describe account")
    public String describeAccount() {

        if (tokenCollector.getTokens().isEmpty()) {
            return "You are not signed in";
        } else {
            String userName = sessionManager.getSessionUsername(
                    tokenCollector.getTokens().get(tokenCollector.getTokens().size() - 1));

            boolean isSessionPrivileged = sessionManager.isPrivilegedSession(
                    tokenCollector.getTokens().get(tokenCollector.getTokens().size() - 1));

            if (isSessionPrivileged) {
                return "Signed in with privileged account '" + userName + "'";
            } else {
                return "Signed in with account '" + userName + "'";
            }
        }
    }
}