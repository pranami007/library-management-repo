package com.library.management.exception;

public class ResourceNotFoundException extends LibraryException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(int code, String errorMessage){
        super(code,errorMessage);
    }

}
