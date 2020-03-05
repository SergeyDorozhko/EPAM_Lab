package com.epam.lab.exception;

public class AuthorNotFoundException extends RepositoryException {
    private static final String MESSAGE = "Author not found";

   public AuthorNotFoundException() {
        super(MESSAGE);
    }
}
