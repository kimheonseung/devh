package com.devh.common.exception;

public class JwtExpiredException extends Exception {

    public JwtExpiredException(String message) {
        super(message);
    }
    
}
