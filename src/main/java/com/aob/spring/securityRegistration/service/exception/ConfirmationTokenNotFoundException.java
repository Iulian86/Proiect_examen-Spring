package com.aob.spring.securityRegistration.service.exception;

public class ConfirmationTokenNotFoundException extends RuntimeException{
    public ConfirmationTokenNotFoundException(String message) {
        super(message);
    }
}
