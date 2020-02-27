package com.epam.lab.exception;

public abstract class RepositoryException extends RuntimeException {

    public RepositoryException(String message) {
        super(message);
    }
}
