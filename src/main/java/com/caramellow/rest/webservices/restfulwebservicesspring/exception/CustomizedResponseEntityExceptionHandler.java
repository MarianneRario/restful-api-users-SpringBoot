package com.caramellow.rest.webservices.restfulwebservicesspring.exception;

import com.caramellow.rest.webservices.restfulwebservicesspring.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.stream.Collectors;

@RestController // provides a response back in case of exceptions
@ControllerAdvice // must be applicable to all other controllers
public class CustomizedResponseEntityExceptionHandler
    extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final org.springframework.http.ResponseEntity<java.lang.Object>
    handleAllException(
            java.lang.Exception ex,
            org.springframework.web.context.request.WebRequest request)
            throws java.lang.Exception {

        // create an instance of specific bean (ExceptionResponse.java)
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),
                        ex.getMessage(), request.getDescription(true));

        // create a response entity object
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(UserNotFoundException.class)
    public final org.springframework.http.ResponseEntity<java.lang.Object>
    handleUserNotFoundException(
            UserNotFoundException ex,
            org.springframework.web.context.request.WebRequest request)
            throws java.lang.Exception {

        // create an instance of specific bean (ExceptionResponse.java)
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        new Date(),
                        ex.getMessage(),
                        request.getDescription(true));

        // create a response entity object
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);

    }

    @Override // this will give an information about what kind of error we got; client friendly
    protected org.springframework.http.ResponseEntity<java.lang.Object>
    handleMethodArgumentNotValid(
            org.springframework.web.bind.MethodArgumentNotValidException ex,
            org.springframework.http.HttpHeaders headers,
            org.springframework.http.HttpStatus status,
            org.springframework.web.context.request.WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        new Date(),
                        "Validation Error",
                        ex.getBindingResult().getFieldErrors() // give the consumer the details of what went wrong in the request using getBindingResult
                                .stream()
                                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                                .collect(Collectors.joining("; ")));
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);

    }

}
