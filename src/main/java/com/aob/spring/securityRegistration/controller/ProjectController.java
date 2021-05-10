package com.aob.spring.securityRegistration.controller;

import com.aob.spring.securityRegistration.dto.MinimumUserInfoDto;
import com.aob.spring.securityRegistration.dto.ProjectDtoRequest;
import com.aob.spring.securityRegistration.dto.ProjectDtoResponse;
import com.aob.spring.securityRegistration.repository.model.Project;
import com.aob.spring.securityRegistration.repository.model.SubTaskStatus;
import com.aob.spring.securityRegistration.service.EmailService;
import com.aob.spring.securityRegistration.service.ProjectService;
import com.aob.spring.securityRegistration.util.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/projects")
public class ProjectController {

    private ProjectService projectService;
    private EmailService emailService;

    public ProjectController(@Autowired ProjectService projectService,
                             @Autowired EmailService emailService) {
        this.projectService = projectService;
        this.emailService = emailService;
    }

    @PostMapping
    public ProjectDtoResponse save(@RequestBody ProjectDtoRequest projectRequest) {
        // --- conversie de la Request la Entity
        Project projectRequestEntity = mapProjectRequestDtoToProject(projectRequest);
        // --- are loc actiunea (in cazul salvarea in db
        Project project = projectService.save(projectRequestEntity);
        // --- conversie de la entity la response
        return mapProjectToProjectDtoResponse(project);
    }

    @GetMapping(path = "/{id}")
    public ProjectDtoResponse getById(@PathVariable Long id) {
        return mapProjectToProjectDtoResponse(projectService.findById(id));
    }

    @GetMapping(path = "/all")
    public List<ProjectDtoResponse> getAll() {
        List<ProjectDtoResponse> result = new ArrayList<>();
        projectService.findAll().forEach(project -> result.add(mapProjectToProjectDtoResponse(project)));
        return result;
    }

    @GetMapping(path = "/users")
    public ProjectDtoResponse update(@RequestParam("projectName") String projectName, @RequestParam("username") String username) {
        Project project = projectService.assignUserToProject(username, projectName);
        return mapProjectToProjectDtoResponse(project);
    }

    @GetMapping(path = "/assign-users")
    public HttpStatus invite(@RequestParam("projectName") String projectName, @RequestParam("username") String username) {
        projectService.sendInviteLinkToUserForProjectAssignment(username, projectName);
        return HttpStatus.OK;
    }

    @GetMapping(path = "/paginated-projects")
    public Page<Project> getAllPaginated(@RequestParam("size") int size, @RequestParam ("page") int page) {
        return projectService.findAllPaginated(size, page);
    }

    @PutMapping(path = "/{id}")
    public ProjectDtoResponse update(@PathVariable (value = "id") Long id, @RequestBody ProjectDtoRequest projectDtoRequest) {
        Project project =  projectService.update( id, projectDtoRequest);
        return mapProjectToProjectDtoResponse(project);
    }

    @DeleteMapping (path = "/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        projectService.delete(id);
        return HttpStatus.OK;
    }

    private Project mapProjectRequestDtoToProject(ProjectDtoRequest projectDtoRequest) {
        Project project = new Project();
        project.setLocation(projectDtoRequest.getName());
        project.setName(projectDtoRequest.getLocation());
        project.setStartDate(projectDtoRequest.getStartDate());
        project.setFinishedDate(projectDtoRequest.getFinishedDate());
        return project;
    }

    private ProjectDtoResponse mapProjectToProjectDtoResponse(Project project) {
        MinimumUserInfoDto minimumUserInfoDto = MappingUtils.mapUserToUserDetailsProjectDto(project.getUser());
        List<MinimumUserInfoDto> assignedUsers = new ArrayList<>();
        project.getAssignedUsers().forEach( user -> {
            assignedUsers.add(MappingUtils.mapUserToUserDetailsProjectDto(user));
        });
        return new ProjectDtoResponse(project.getId(), project.getName(), project.getLocation(),
                project.getRegisteredOn(), project.getStartDate(), project.getFinishedDate(),
                minimumUserInfoDto, assignedUsers);
    }
}
