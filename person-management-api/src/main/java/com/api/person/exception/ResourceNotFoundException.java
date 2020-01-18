package com.api.person.exception;

public class ResourceNotFoundException extends  RuntimeException {

    public  ResourceNotFoundException(String errorMessage){
        super(errorMessage);
    }
}