package com.api.common.exception;

public class ResourceNotFoundException extends  RuntimeException {

    public  ResourceNotFoundException(String errorMessage){
        super(errorMessage);
    }
}