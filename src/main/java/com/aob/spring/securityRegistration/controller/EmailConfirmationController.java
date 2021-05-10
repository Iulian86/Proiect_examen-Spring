package com.aob.spring.securityRegistration.controller;

import com.aob.spring.securityRegistration.repository.model.User;
import com.aob.spring.securityRegistration.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EmailConfirmationController {

    private UserService userService;

    public EmailConfirmationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/confirm/{confirmationToken}")
    public User confirmationOfEmail(@PathVariable String confirmationToken) {
        return userService.confirmUser(confirmationToken);
    }

}
