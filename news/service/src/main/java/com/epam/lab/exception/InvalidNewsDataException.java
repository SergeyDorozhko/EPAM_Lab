package com.epam.lab.exception;

public class InvalidNewsDataException extends ServiceException {

    private static final String MESSAGE = "Invalid news data";

    public InvalidNewsDataException() {
        super(MESSAGE);
    }

}
