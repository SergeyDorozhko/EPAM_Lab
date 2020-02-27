package com.epam.lab.controller;

import com.epam.lab.exception.RepositoryException;
import com.epam.lab.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

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
    public ResponseEntity<Object> errorService( ServiceException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        body.put("status", HttpStatus.CONFLICT);
        body.put("errors", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<Object> errorRepository(RepositoryException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        body.put("status", HttpStatus.NOT_FOUND);
        body.put("errors", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        body.put("status", status.value());
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        body.put("errors", errors);
        return new ResponseEntity<Object>(body, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> validateIdException(ConstraintViolationException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("error", e.getMessage().split("([\\w.<>\\[\\] ]+[:]{1}[ ]{1})|([,]{1})"));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> errorFatal( Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
