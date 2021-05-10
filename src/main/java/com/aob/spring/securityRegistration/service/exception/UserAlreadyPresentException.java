package com.aob.spring.securityRegistration.service.exception;

public class UserAlreadyPresentException extends RuntimeException {
    public UserAlreadyPresentException(String errorMessage) {
        super(errorMessage);
    }
}
