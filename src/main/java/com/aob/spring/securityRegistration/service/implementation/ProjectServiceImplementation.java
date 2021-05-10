package com.aob.spring.securityRegistration.service.implementation;

import com.aob.spring.securityRegistration.dto.ProjectDtoRequest;
import com.aob.spring.securityRegistration.repository.ProjectRepository;
import com.aob.spring.securityRegistration.repository.UserRepository;
import com.aob.spring.securityRegistration.repository.model.Project;
import com.aob.spring.securityRegistration.repository.model.ProjectSubTask;
import com.aob.spring.securityRegistration.repository.model.User;
import com.aob.spring.securityRegistration.service.EmailService;
import com.aob.spring.securityRegistration.service.ProjectService;
import com.aob.spring.securityRegistration.service.exception.ResourceAlreadyPresentException;
import com.aob.spring.securityRegistration.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectServiceImplementation implements ProjectService {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    private EmailService emailService;

    public ProjectServiceImplementation(@Autowired ProjectRepository projectRepository,
                                        @Autowired UserRepository userRepository,
                                        @Autowired EmailService emailService) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public Project save(Project project) {
        if (projectRepository.findByName(project.getName()) == null) {
            org.springframework.security.core.userdetails.User principal =
                    (org.springframework.security.core.userdetails.User) SecurityContextHolder
                                                .getContext() //luam context-ul
                                                .getAuthentication() //luam autentificarea
                                                .getPrincipal();  //luam user-ul logat

            String loggedUsername = principal.getUsername();
            User user = userRepository.findByUsername(loggedUsername);
            project.setAdministrator(user);
            return projectRepository.save(project);
        } else {
            throw new ResourceAlreadyPresentException(
                    String.format("Resource of type %s already exists in db with name %s", "project", project.getId()));
        }
    }

    @Override
    public Project findByName(String name) {
        Project project = projectRepository.findByName(name);
        if (project != null) {
            return project;
        } else {
            throw new ResourceNotFoundException(String.format("Resource %s with name %s not found", "project", name));
        }
    }

    @Override
    public Project findById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            return project.get();
        } else {
            throw new ResourceNotFoundException(String.format("Resource %s with name %d not found", "project", id));
        }
    }


    @Override
    public Project assignUserToProject(String username, String projectName) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException(String.format("Resource %s with id %s not found", "user", username));
        }
        Project project = projectRepository.findByName(projectName);
        if (project == null) {
            throw new ResourceNotFoundException(String.format("Resource %s with id %s not found", "project", projectName));
        }
        Set<User> assignedUsers = project.getAssignedUsers();
        assignedUsers.add(user);
        project.setAssignedUsers(assignedUsers);
        Project updatedProject = projectRepository.save(project);
//        emailService.sendEmail(user.getEmail(), String.format("You have been assigned to project with name %s",
//                project.getName()));
        return updatedProject;
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public void sendInviteLinkToUserForProjectAssignment(String username, String projectName) {
        if (isUserAndProjectPresent(username, projectName)) {
            User user = userRepository.findByUsername(username);
            // http://localhost:8081/projects/users?projectName=projectName&username=username;
            emailService
                    .sendEmail(user.getEmail(),
                               String.format("You have been invited to the project. Please " +
                                               "access the link to be assigned. " +
                                               "http://localhost:9191/projects/users?username=%s&projectName=%s",
                               username, projectName));
        }
    }

    @Override
    public Page<Project> findAllPaginated(int size, int page) {
        return projectRepository.findAll(PageRequest.of(page,size));
    }

    private boolean isUserAndProjectPresent(String username, String projectName) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException(String.format("Resource %s with id %s not found", "user", username));
        }
        Project project = projectRepository.findByName(projectName);
        if (project == null) {
            throw new ResourceNotFoundException(String.format("Resource %s with id %s not found", "project", projectName));
        }
        return true;
    }


    @Override
    public Project update( Long id, ProjectDtoRequest updatedProject) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            Project projectInDatabase = project.get();
            if (updatedProject.getName() != null) {
                projectInDatabase.setName(updatedProject.getName());
            }
            if (updatedProject.getLocation() != null) {
                projectInDatabase.setLocation(updatedProject.getLocation());
            }
            if (updatedProject.getStartDate() != null) {
                projectInDatabase.setStartDate(updatedProject.getStartDate());
            }
            if (updatedProject.getFinishedDate() != null) {
                projectInDatabase.setFinishedDate(updatedProject.getFinishedDate());
            }
            return projectRepository.save(projectInDatabase);
        } else {
            throw new ResourceNotFoundException(String.format("Resource %s with id %d not found", "project", id));
        }
    }

    @Override
    public void delete(Long id) {
        if (projectRepository.findById(id).isPresent()) {
            projectRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(String.format("Resource %s with id %d not found", "project", id));
        }
    }
}
