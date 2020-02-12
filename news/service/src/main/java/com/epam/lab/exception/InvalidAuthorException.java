package com.epam.lab.exception;

public class InvalidAuthorException extends ServiceException {
    private static final String MESSAGE = "Invalid author";

    public InvalidAuthorException() {
        super(MESSAGE);
    }
}
