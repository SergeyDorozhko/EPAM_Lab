package com.epam.lab.controller;

import com.epam.lab.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

@ControllerAdvice
public class ExceptionHandlerController {

    private ResourceBundle resourceBundle;

    @Autowired
    public ExceptionHandlerController(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public void errorFindQuery(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value(), resourceBundle.getString("msg.noContent"));
    }

    @ExceptionHandler(ServiceException.class)
    public void errorSaveTag(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value(), resourceBundle.getString("msg.saveTag"));
    }


    @ExceptionHandler(Exception.class)
    public void errorFatal(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), resourceBundle.getString("msg.exception"));
    }

}
