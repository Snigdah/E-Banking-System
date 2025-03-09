package com.salary.management.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static com.salary.management.utils.Constants.Response.DATA_KEY;
import static com.salary.management.utils.Constants.Response.MESSAGE_KEY;
import static com.salary.management.utils.Constants.Response.META_DATA;
import static com.salary.management.utils.Constants.Response.STATUS_CODE;
import static com.salary.management.utils.Constants.Response.STATUS_KEY;

/**
 * Utility class for generating consistent API responses.
 */
public class ResponseHandler {
    private ResponseHandler() {
        // Private constructor to prevent instantiation
    }

    /**
     * Generates a structured API response with metadata and optional data.
     *
     * @param message     The response message.
     * @param status      The HTTP status.
     * @param responseObj The response data (optional).
     * @return A ResponseEntity containing the structured response.
     */
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> response = new HashMap<String, Object>();
        Map<String, Object> meta = new HashMap<String, Object>();

        meta.put(STATUS_KEY, status);
        meta.put(STATUS_CODE, status.value());
        meta.put(MESSAGE_KEY, message);

        response.put(META_DATA, meta);

        if(responseObj != null){
            response.put(DATA_KEY, responseObj);
        }

        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }


    /**
     * Overloaded method for generating a response without data, such as for delete operations.
     *
     * @param message The response message.
     * @param status  The HTTP status.
     * @return A ResponseEntity containing the structured response.
     */
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status) {
        return generateResponse(message, status, null);
    }
}