package com.epam.lab.exception;

public class NewsWithoutAuthorException extends RepositoryException {
    private static final String MESSAGE = "News have no author";
    public NewsWithoutAuthorException() {
        super(MESSAGE);
    }
}
