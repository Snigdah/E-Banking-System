package com.salary.management.exception;

import com.salary.management.response.ErrorDetails;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler provides centralized exception handling across all @RequestMapping methods.
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles CustomException and wraps it in a ResponseEntity.
     *
     * @param exception the exception instance captured
     * @param webRequest the web request during which the exception was raised
     * @return a ResponseEntity containing ErrorDetails and the appropriate HTTP status
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDetails> accessDenied(CustomException exception,
                                                     WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return ResponseEntity.status(exception.getStatus()).body(errorDetails);
    }

    /**
     * Handles MethodArgumentNotValidException, providing detailed error messages for each invalid field.
     *
     * @param ex the MethodArgumentNotValidException instance
     * @param headers the headers to be written to the response
     * @param status the HTTP status code
     * @param request the current web request
     * @return a ResponseEntity containing field errors and 400 BAD REQUEST status
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles ResourceNotFoundException and returns a friendly error response.
     *
     * @param exception the ResourceNotFoundException instance
     * @param webRequest the web request during which the exception was raised
     * @return a ResponseEntity containing ErrorDetails and NOT_FOUND status
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }
}
