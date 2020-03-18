package com.epam.lab.exception;

public class UserNotFoundException extends RepositoryException {

    private static final String MESSAGE = "User not found";

    public UserNotFoundException(){
        super(MESSAGE);
    }
}
