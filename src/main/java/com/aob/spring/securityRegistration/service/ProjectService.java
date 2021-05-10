package com.aob.spring.securityRegistration.service;
import com.aob.spring.securityRegistration.dto.ProjectDtoRequest;
import com.aob.spring.securityRegistration.repository.model.Project;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectService {

    Project save(Project project);
    Project findByName(String name);
    Project findById(Long id);
    Project assignUserToProject(String username, String projectName);
    List<Project> findAll();
    void sendInviteLinkToUserForProjectAssignment(String username, String projectName);
    Page<Project> findAllPaginated(int size, int page);
    Project update( Long id, ProjectDtoRequest project);
    void delete(Long id);



}
