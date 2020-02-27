package com.epam.lab.exception;

public class TagNotCreatedException extends RepositoryException {
private static final String MESSAGE = "invalid tag";

    public TagNotCreatedException() {
        super(MESSAGE);
    }
}
