package com.caramellow.rest.webservices.restfulwebservicesspring.exception;

import java.util.Date;

public class ExceptionResponse {
    /* create these variables */
    // timestamp - indicates when the exception happens
    // message
    // detail
    private Date timestamp;
    private String message;
    private String details;

    public ExceptionResponse(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
