package com.library.management.exception;

public class DataValidationException extends LibraryException {

    private static final long serialVersionUID = 1L;

    public DataValidationException(int code, String errorMessage) {
        super(code, errorMessage);
    }
}
