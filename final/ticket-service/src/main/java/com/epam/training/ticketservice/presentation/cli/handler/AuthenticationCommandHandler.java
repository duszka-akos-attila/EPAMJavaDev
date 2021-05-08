package com.epam.training.ticketservice.presentation.cli.handler;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class AuthenticationCommandHandler {

    @ShellMethod(value = "Signing into a privileged account", key = "sign in privileged")
    public String signInPrivileged(String privilegedUserName, String privilegedUserPassword){
        return "Welcome "+ privilegedUserName +"!";
    }

    @ShellMethod(value = "Signing out from an account", key = "sign out")
    public String signOut(){
        return "You are now signed out!";
    }

    @ShellMethod(value = "Describes a logged in account", key = "describe account")
    public String describeAccount(){
        String userName = "admin";
        if (userName != null) {
            return "Signed in with privileged account " + userName;
        }

        return "You are not signed in!";
    }
}