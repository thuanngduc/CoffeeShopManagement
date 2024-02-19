package com.project.qlquancoffeeapi.exception;

public class UsernameNotFoundException extends Exception {
    public UsernameNotFoundException(String userNotFoundWithUsername) {
        super(userNotFoundWithUsername);
    }
}
