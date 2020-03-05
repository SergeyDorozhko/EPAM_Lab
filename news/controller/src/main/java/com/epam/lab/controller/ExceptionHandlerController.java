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
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private static final String MSG_NO_CONTENT = "msg.noContent";
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String REGEX_FOR_CUT_UNNEEDED_INFORMATION = "([\\w.<>\\[\\] ]+[:]{1}[ ]{1})|([,]{1})";
    private ResourceBundle resourceBundle;

    @Autowired
    public ExceptionHandlerController(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public void errorFindQuery(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value(), resourceBundle.getString(MSG_NO_CONTENT));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> errorService( ServiceException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        body.put(STATUS, HttpStatus.CONFLICT);
        body.put(ERROR, ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<Object> errorRepository(RepositoryException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        body.put(STATUS, HttpStatus.NOT_FOUND);
        body.put(ERROR, ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        body.put(STATUS, status.value());
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        body.put(ERROR, errors);
        return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> validateIdException(ConstraintViolationException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        body.put(STATUS, HttpStatus.BAD_REQUEST);
        body.put(ERROR, e.getMessage().split(REGEX_FOR_CUT_UNNEEDED_INFORMATION));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> errorFatal( Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        body.put(STATUS, HttpStatus.BAD_REQUEST);
        body.put(ERROR, ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
