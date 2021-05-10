package com.aob.spring.securityRegistration.util;



import com.aob.spring.securityRegistration.dto.MinimumUserInfoDto;
import com.aob.spring.securityRegistration.repository.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class MappingUtils {

    public static MinimumUserInfoDto mapUserToUserDetailsProjectDto(User user) {
        List<String> roles = user.getRoles().stream().map(userRole -> userRole.getRole()).collect(Collectors.toList());
        return new MinimumUserInfoDto(user.getUserId(), user.getUsername(), user.getEmail(), roles);
    }

}
