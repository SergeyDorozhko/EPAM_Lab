package com.epam.lab.exception;

public class DuplicateTagException extends ServiceException {
    private static final  String MESSAGE = "This tag already exist";
    public DuplicateTagException(){
        super(MESSAGE);
    }
}
