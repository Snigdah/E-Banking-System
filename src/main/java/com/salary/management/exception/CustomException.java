package com.salary.management.exception;

import org.springframework.http.HttpStatus;

/**
 * CustomException is a custom runtime exception used to handle
 * specific application errors with an associated HTTP status code.
 */
public class CustomException extends RuntimeException{
    private HttpStatus status;
    private String message;

    /**
     * Constructs a new CustomException with the specified HTTP status and error message.
     *
     * @param status  the HTTP status code
     * @param message the detailed error message
     */
    public CustomException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * Constructs a new CustomException with the specified error message and HTTP status.
     * This constructor allows overriding the superclass message.
     *
     * @param message  the error message for the superclass
     * @param status   the HTTP status code
     * @param message1 the detailed error message to store
     */
    public CustomException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message =  message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
