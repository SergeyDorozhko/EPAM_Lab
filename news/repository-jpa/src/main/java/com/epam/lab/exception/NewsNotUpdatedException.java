package com.epam.lab.exception;

public class NewsNotUpdatedException extends RepositoryException {
    private static final String MESSAGE = "News was not updated";

    public NewsNotUpdatedException(){
        super(MESSAGE);
    }
}
