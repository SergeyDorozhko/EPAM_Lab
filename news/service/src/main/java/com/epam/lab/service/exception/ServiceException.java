package com.epam.lab.service.exception;

public class ServiceException extends RuntimeException {

    public ServiceException(){}

    public ServiceException(String message) {
        super(message);
    }



}
