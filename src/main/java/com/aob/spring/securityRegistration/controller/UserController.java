package com.aob.spring.securityRegistration.controller;

import com.aob.spring.securityRegistration.dto.UserDto;
import com.aob.spring.securityRegistration.repository.model.User;
import com.aob.spring.securityRegistration.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User save(@RequestBody UserDto userDto) {
        User user = userService.mapUserDtoToUser(userDto);
        return userService.save(user);
    }

    @GetMapping(path = "/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping
    public User findByEmail(@RequestParam("email") String email){
        return userService.findByEmail(email);
    }

    @GetMapping(path = "/logged-user")
    public HttpStatus getLoggedUser() {
        return HttpStatus.OK;
    }

}
