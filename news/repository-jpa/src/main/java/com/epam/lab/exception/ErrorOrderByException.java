package com.epam.lab.exception;

public class ErrorOrderByException extends RepositoryException {

    private static final String MESSAGE = "Invalid sort by value";

    public ErrorOrderByException(){
        super(MESSAGE);
    }
}
