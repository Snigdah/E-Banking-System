package com.salary.management.response;

import java.util.Date;

/**
 * A class representing the structure of error details
 * that will be returned in API error responses.
 */
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

    /**
     * Constructor to initialize the ErrorDetails object.
     *
     * @param timestamp The time when the error occurred.
     * @param message The error message.
     * @param details Additional details about the error.
     */
    public ErrorDetails(Date timestamp, String message, String details) {
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
