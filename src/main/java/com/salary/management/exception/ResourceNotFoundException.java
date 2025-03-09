package com.salary.management.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ResourceNotFoundException is thrown when a requested resource is not found.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private String resourceName;
    private String fieldName;
    private long fieldValue;


    /**
     * Constructs a new ResourceNotFoundException.
     *
     * @param resourceName the name of the resource that was not found
     * @param fieldName    the name of the field that was searched
     * @param fieldValue   the value of the field that was searched
     */
    public ResourceNotFoundException (String resourceName, String fieldName, long fieldValue){
        super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public long getFieldValue() {
        return fieldValue;
    }
}