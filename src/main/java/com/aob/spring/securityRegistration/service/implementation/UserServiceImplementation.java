package com.aob.spring.securityRegistration.service.implementation;

import com.aob.spring.securityRegistration.dto.UserDto;
import com.aob.spring.securityRegistration.repository.RoleRepository;
import com.aob.spring.securityRegistration.repository.UserRepository;
import com.aob.spring.securityRegistration.repository.model.User;
import com.aob.spring.securityRegistration.repository.model.Role;
import com.aob.spring.securityRegistration.repository.model.UserType;
import com.aob.spring.securityRegistration.service.EmailService;
import com.aob.spring.securityRegistration.service.UserService;
import com.aob.spring.securityRegistration.service.exception.ConfirmationTokenNotFoundException;
import com.aob.spring.securityRegistration.service.exception.RoleNotFoundException;
import com.aob.spring.securityRegistration.service.exception.UserAlreadyPresentException;
import com.aob.spring.securityRegistration.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    private EmailService emailService;
    private PasswordEncoder bCryptPasswordEncoder;
    private RoleRepository roleRepository;

    public UserServiceImplementation(@Autowired UserRepository userRepository, @Autowired EmailService emailService,
                                     @Autowired RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.roleRepository = roleRepository;
    }

    @Override
    public User save(User user) {
        boolean isFoundByEmail = userRepository.findByEmail(user.getEmail()) != null;
        boolean isFoundByUsername = userRepository.findByUsername(user.getUsername()) != null;
        if (isFoundByEmail) {
            throw new UserAlreadyPresentException("1001");
        }
        if (isFoundByUsername) {
            throw new UserAlreadyPresentException("1002");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User db =  userRepository.save(user);
        String message = String.format("Hello %s, confirm your email at http://localhost:9191/confirm/%s", db.getUsername(), db.getConfirmationToken());
        //emailService.sendEmail(db.getEmail(), message);
        return db;
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("User with id not found");
        }
    }

    @Override
    public User findByConfirmationToken(String confirmationToken) {
        User user = userRepository.findByConfirmationToken(confirmationToken);
        if (user != null) {
            return user;
        } else {
            throw new ConfirmationTokenNotFoundException("Confirmation token not found");
        }
    }

    @Override
    public User confirmUser(String confirmationToken) {
        User user = findByConfirmationToken(confirmationToken);
        if (user != null) {
            user.setEmailConfirmed(true);
            return update(user);
        } else {
            throw new RuntimeException("Something went wrong");
        }
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException(String.format("User with name %s was not found", username));
        }
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            throw new UserNotFoundException(String.format("User with %d was not found", id));
        }
    }

    @Override
    public User mapUserDtoToUser(UserDto userDto) {
        User user = new User(userDto.getUsername(), userDto.getEmail(), userDto.getPassword());
        user.setRoles(setUserRolesBasedOnUserType(userDto.getUserType()));
        return user;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    private Set<Role> setUserRolesBasedOnUserType(UserType userType) {
        Role role = roleRepository.findByRole(userType.name());
        Set<Role> roles = new HashSet<>();
        if (role != null) {
            roles.add(role);
            return roles;
        } else {
            throw new RoleNotFoundException(String.format("Role with name %s was not found in db", userType.name()));
        }
    }
}
