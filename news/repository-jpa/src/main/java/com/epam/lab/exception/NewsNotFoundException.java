package com.epam.lab.exception;

public class NewsNotFoundException extends RepositoryException {

    private static final String MESSAGE = "News with such id does not exist";

    public NewsNotFoundException(){
        super(MESSAGE);
    }

}
