package com.epam.lab.exception;

public class TagNotFoundException extends RepositoryException {
    private static final String MESSAGE = "Tag not found";
    TagNotFoundException() {
        super(MESSAGE);
    }
}
