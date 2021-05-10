package com.aob.spring.securityRegistration.service;


import com.aob.spring.securityRegistration.dto.UserDto;
import com.aob.spring.securityRegistration.repository.model.User;

public interface UserService {

    User save(User user);
    User findById(Long id);
    User findByConfirmationToken(String confirmationToken);
    User confirmUser(String confirmationToken);
    User findByUsername(String username);
    User update(User user);
    void delete(Long id);
    User mapUserDtoToUser(UserDto userDto);
    User findByEmail(String email);
}
