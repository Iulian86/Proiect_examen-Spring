package com.aob.spring.securityRegistration.service.exception;

public class ResourceAlreadyPresentException extends RuntimeException{

    public ResourceAlreadyPresentException(String message) {
        super(message);
    }

}
