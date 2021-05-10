package com.aob.spring.securityRegistration.controller;

import com.aob.spring.securityRegistration.dto.*;
import com.aob.spring.securityRegistration.repository.model.*;
import com.aob.spring.securityRegistration.service.*;
import com.aob.spring.securityRegistration.service.exception.ResourceNotFoundException;
import com.aob.spring.securityRegistration.util.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/project-subtask")
public class ProjectSubtaskController {

    private ProjectSubtaskService projectSubtaskService;
    private ProjectTaskService projectTaskService;
    private UserService userService;
    private ProjectService projectService;

    public ProjectSubtaskController(@Autowired ProjectSubtaskService projectSubtaskService,
                                    @Autowired ProjectTaskService projectTaskService,
                                    @Autowired UserService userService,
                                    @Autowired ProjectService projectService) {
        this.projectSubtaskService = projectSubtaskService;
        this.projectTaskService = projectTaskService;
        this.userService = userService;
        this.projectService = projectService;
    }

    @PostMapping
    public ProjectSubtaskDtoResponse save(@RequestBody ProjectSubtaskDtoRequest projectSubtaskDtoRequest) {
        ProjectSubTask projectSubTask = mapProjectSubtaskDtoRequestToProjectSubtask(projectSubtaskDtoRequest);
        ProjectSubTask savedInDb = projectSubtaskService.save(projectSubTask);
        return mapProjectSubtaskEntityToDtoResponse(savedInDb);
    }

    @GetMapping(path = "/{id}")
    public ProjectSubtaskDtoResponse getById(@PathVariable Long id) {
        return mapProjectSubtaskEntityToDtoResponse(projectSubtaskService.findById(id));
    }

    @GetMapping(path = "/all")
    public List<ProjectSubtaskDtoResponse> getAll() {
        List<ProjectSubTask> projectSubTasks = projectSubtaskService.findAll();
        List<ProjectSubtaskDtoResponse> result = new ArrayList<>();
        projectSubTasks.forEach(projectSubTask -> {
            result.add(mapProjectSubtaskEntityToDtoResponse(projectSubTask));
        });
        return result;
    }

    @GetMapping
    public List<ProjectSubtaskDtoResponse> findAllByName(@RequestParam("name") String name, @RequestParam("projectId") Long projectId) {
        List<ProjectSubTask> projectSubTasks = projectSubtaskService.findByNameContainsAndProjectId(name, projectId);
        List<ProjectSubtaskDtoResponse> responseList = new ArrayList<>();
        projectSubTasks.forEach(projectSubTask -> {
            responseList.add(mapProjectSubtaskEntityToDtoResponse(projectSubTask));
        });
        return responseList;
    }

    @GetMapping(path = "/project/{id}")
    public List<ProjectSubtaskDtoResponse> findAllByProjectId(@PathVariable("id") Long projectId) {
        List<ProjectSubTask> projectSubTasks = projectSubtaskService.findAllByProjectId(projectId);
        List<ProjectSubtaskDtoResponse> responseList = new ArrayList<>();
        projectSubTasks.forEach(projectSubTask -> {
            responseList.add(mapProjectSubtaskEntityToDtoResponse(projectSubTask));
        });
        return responseList;
    }

    @GetMapping(path = "/project-task/{id}")
    public List<ProjectSubtaskDtoResponse> findAllByProjectTaskId(@PathVariable("id") Long projectTaskId) {
        List<ProjectSubTask> projectSubTasks = projectSubtaskService.findAllByProjectTaskId(projectTaskId);
        List<ProjectSubtaskDtoResponse> responseList = new ArrayList<>();
        projectSubTasks.forEach(projectSubTask -> {
            responseList.add(mapProjectSubtaskEntityToDtoResponse(projectSubTask));
        });
        return responseList;
    }

    @GetMapping(path = "/users")
    public ProjectSubtaskDtoResponse update(@RequestParam("subtaskName") String subtaskName, @RequestParam("username") String username) {
        ProjectSubTask projectSubTask = projectSubtaskService.assignUserToSubtask(username, subtaskName);
        return mapProjectSubtaskEntityToDtoResponse(projectSubTask);
    }

    @GetMapping(path = "/assign-users")
    public HttpStatus invite(@RequestParam("subtaskName") String subtaskName, @RequestParam("username") String username) {
        projectSubtaskService.sendInviteLinkToUserForSubtaskAssignment(username, subtaskName);
        return HttpStatus.OK;
    }

//    @PutMapping(path = "/{id}")
//    public ProjectSubtaskDtoResponse assignProjectSubtaskToUser(@PathVariable Long id,
//                                                     @RequestParam("username") String username) {
//        ProjectSubTask projectSubTask = projectSubtaskService.findById(id);
//        return mapProjectSubtaskEntityToDtoResponse(projectSubtaskService.assignProjectSubtaskToUser(projectSubTask, username));
//    }

    @PutMapping(path = "/status/{id}")
    public ProjectSubtaskDtoResponse updateStatus(@PathVariable Long id, @RequestParam("status") SubTaskStatus status) {
        return mapProjectSubtaskEntityToDtoResponse(projectSubtaskService.updateStatus(id, status));
    }

    @PutMapping(path = "/edit-subtask/{id}")
    public ProjectSubtaskDtoResponse update(@PathVariable Long id,
                                            @RequestBody ProjectSubtaskDtoRequest projectSubtaskDtoRequest){
        ProjectSubTask projectSubTask = projectSubtaskService.update(id, projectSubtaskDtoRequest);
        return mapProjectSubtaskEntityToDtoResponse(projectSubTask);
    }

    @DeleteMapping (path = "/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        projectSubtaskService.delete(id);
        return HttpStatus.OK;
    }


//    @GetMapping(path = "/project_subtasks")
//    public List<ProjectSubtaskDtoResponse> findAllByName(@RequestParam("name") String name, @RequestParam("projectId") Long projectTaskId) {
//        List<ProjectSubTask> projectSubTasks = projectSubtaskService.findByNameContainsAndProjectTaskId(name, projectTaskId);
//        List<ProjectSubtaskDtoResponse> responseList = new ArrayList<>();
//        projectSubTasks.forEach(projectSubTask -> {
//            responseList.add(mapProjectSubtaskEntityToProjectSubtaskDtoResponse(projectSubTask));
//        });
//        return responseList;
//    }


    private ProjectSubTask mapProjectSubtaskDtoRequestToProjectSubtask
            (ProjectSubtaskDtoRequest projectSubtaskDtoRequest) {
        ProjectSubTask projectSubTask = new ProjectSubTask();

        projectSubTask.setName(projectSubtaskDtoRequest.getName());
        projectSubTask.setStartDate(projectSubtaskDtoRequest.getStartDate());
        projectSubTask.setTargetDate(projectSubtaskDtoRequest.getTargetDate());
        projectSubTask.setStatus(projectSubtaskDtoRequest.getStatus());
        projectSubTask.setWeight(projectSubtaskDtoRequest.getWeight());
//        Project project = projectService.findById(projectSubtaskDtoRequest.getProjectId());
//        projectSubTask.setProject(project);
//        ProjectTask projectTask = projectTaskService.findById(projectSubtaskDtoRequest.getProjectTaskId());
//        projectSubTask.setProjectTask(projectTask);
//        Project project = projectService.findById(projectSubtaskDtoRequest.getProjectId());
//        projectSubTask.setProject(project);

        if (projectSubtaskDtoRequest.getUsername() != null) {
            User user = userService.findByUsername(projectSubtaskDtoRequest.getUsername());
            projectSubTask.setAssignedUser(user);
        } else {
            org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User)
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.findByUsername(principal.getUsername());
            projectSubTask.setAssignedUser(user);
        }

//        Project project = projectService.findById(projectSubtaskDtoRequest.getProjectId());
//        if ( project != null) {
//            projectSubTask.setProject(project);
//        } else {
//            throw new ResourceNotFoundException(String.format("Project with id %d not found", projectSubtaskDtoRequest.getProjectId()));
//        }

        ProjectTask projectTask = projectTaskService.findById(projectSubtaskDtoRequest.getProjectTaskId());
        if ( projectTask != null) {
            projectSubTask.setProjectTask(projectTask);
        } else {
            throw new ResourceNotFoundException(String.format("Project task with id %d not found", projectSubtaskDtoRequest.getProjectTaskId()));
        }

        return projectSubTask;
    }

    private ProjectSubtaskDtoResponse mapProjectSubtaskEntityToDtoResponse(ProjectSubTask projectSubTask){
        ProjectSubtaskDtoResponse projectSubtaskDtoResponse = new ProjectSubtaskDtoResponse();

        projectSubtaskDtoResponse.setId(projectSubTask.getId());
        projectSubtaskDtoResponse.setName(projectSubTask.getName());
        projectSubtaskDtoResponse.setStartDate(projectSubTask.getStartDate());
        projectSubtaskDtoResponse.setTargetDate(projectSubTask.getTargetDate());
        projectSubtaskDtoResponse.setStatus(projectSubTask.getStatus());
        projectSubtaskDtoResponse.setStatusPercentage(projectSubTask.getStatusPercentage());
        projectSubtaskDtoResponse.setWeight(projectSubTask.getWeight());
        projectSubtaskDtoResponse.setProjectTaskDto(mapProjectSubtaskEntityToProjectTaskDto(projectSubTask.getProjectTask()));
//        projectSubtaskDtoResponse.setProjectDto(mapProjectSubtaskEntityToProjectDto(projectSubTask.getProject()));

        MinimumUserInfoDto infoDto = MappingUtils.mapUserToUserDetailsProjectDto(projectSubTask.getAssignedUser());
        projectSubtaskDtoResponse.setUserInfo(infoDto);

        List<MinimumUserInfoDto> assignedUsers = new ArrayList<>();
        projectSubTask.getAssignedUsers().forEach( user -> {
            assignedUsers.add(MappingUtils.mapUserToUserDetailsProjectDto(user));
        });
        projectSubtaskDtoResponse.setAssignedUsers(assignedUsers);

        return projectSubtaskDtoResponse;
    }

    private ProjectSubtaskToProjectTaskDtoResponse mapProjectSubtaskEntityToProjectTaskDto(ProjectTask projectTask) {
        ProjectSubtaskToProjectTaskDtoResponse dtoResponse = new ProjectSubtaskToProjectTaskDtoResponse();
        dtoResponse.setName(projectTask.getName());
        dtoResponse.setId(projectTask.getId());
        return dtoResponse;
    }

    private ProjectSubtaskToProjectDtoResponse mapProjectSubtaskEntityToProjectDto(Project project) {
        ProjectSubtaskToProjectDtoResponse dtoResponse = new ProjectSubtaskToProjectDtoResponse();
        dtoResponse.setName(project.getName());
        dtoResponse.setId(project.getId());
        return dtoResponse;
    }

    private List<ProjectSubtaskDtoResponse> mapProjectSubtaskListToDtoResponseList(List<ProjectSubTask> projectSubTaskList) {
        List<ProjectSubtaskDtoResponse> projectSubtaskDtoResponses = new ArrayList<>();
        projectSubTaskList.forEach(projectSubTask -> {
            projectSubtaskDtoResponses.add(mapProjectSubtaskEntityToDtoResponse(projectSubTask));
        });
        return projectSubtaskDtoResponses;
    }
}
