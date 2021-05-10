package com.aob.spring.securityRegistration.controller;

import com.aob.spring.securityRegistration.dto.ErrorMessage;
import com.aob.spring.securityRegistration.service.exception.ResourceAlreadyPresentException;
import com.aob.spring.securityRegistration.service.exception.ResourceNotFoundException;
import com.aob.spring.securityRegistration.service.exception.UserAlreadyPresentException;
import com.aob.spring.securityRegistration.service.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage userNotFoundException(UserNotFoundException exception) {
        return new ErrorMessage("404", exception.getMessage());
    }


    @ExceptionHandler(value = {UserAlreadyPresentException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage userAlreadyPresentException(UserAlreadyPresentException exception) {
        String message = "";
        if (exception.getMessage().equals("1001")) {
            message = "User found by email";
        } else {
            message = "User found by username";
        }
        return new ErrorMessage(message, "2001");
    }

    @ExceptionHandler(value = {ResourceAlreadyPresentException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage resourceAlreadyPresentException(ResourceAlreadyPresentException exception) {
        String message = exception.getMessage();
        return new ErrorMessage(message, "3001");
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(ResourceNotFoundException exception) {
        String message = exception.getMessage();
        return new ErrorMessage(message, "3001");
    }
}
