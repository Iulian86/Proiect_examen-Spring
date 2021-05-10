package com.aob.spring.securityRegistration.service.implementation;

import com.aob.spring.securityRegistration.repository.model.User;
import com.aob.spring.securityRegistration.repository.model.Role;
import com.aob.spring.securityRegistration.service.UserService;
import com.aob.spring.securityRegistration.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SdaUserDetailsService implements UserDetailsService {

    private UserService userService;

    public SdaUserDetailsService(@Autowired UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByUsername(s);
        if (user != null) {
            return createUserDetailFromUserForAuthentication(user, mapUserRolesToGrantedAuthorities(user.getRoles()));
        } else {
            throw new UserNotFoundException(String.format("User with username %s was not found", s));
        }
    }

    private List<GrantedAuthority> mapUserRolesToGrantedAuthorities(Set<Role> roleSet) {
        Set<GrantedAuthority> authoritySet = new HashSet<GrantedAuthority>();
        for (Role role : roleSet) {
            authoritySet.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new ArrayList<>(authoritySet);
    }

    private UserDetails createUserDetailFromUserForAuthentication(User user, List<GrantedAuthority> authoritiesOfUser) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), true, true, true, true,  authoritiesOfUser);
    }
}
