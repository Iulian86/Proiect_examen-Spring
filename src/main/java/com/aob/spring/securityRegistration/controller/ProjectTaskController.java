package com.aob.spring.securityRegistration.controller;

import com.aob.spring.securityRegistration.dto.*;
import com.aob.spring.securityRegistration.repository.model.*;
import com.aob.spring.securityRegistration.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/project-tasks")
public class ProjectTaskController {

    private ProjectTaskService projectTaskService;
    private ProjectSubtaskService projectSubtaskService;
    private ProjectService projectService;

    public ProjectTaskController(@Autowired ProjectTaskService projectTaskService,
                                 @Autowired ProjectSubtaskService projectSubtaskService,
                                 @Autowired ProjectService projectService) {
        this.projectTaskService = projectTaskService;
        this.projectSubtaskService = projectSubtaskService;
        this.projectService = projectService;
    }

    @PostMapping
    public ProjectTaskDtoResponse save(@RequestBody ProjectTaskDtoRequest projectTaskDtoRequest) {
        ProjectTask projectTask = mapProjectTaskDtoRequestToProjectTask(projectTaskDtoRequest);
        ProjectTask projectTaskDb = projectTaskService.save(projectTask);
        return mapProjectTaskEntityToDtoResponse(projectTaskDb);
    }

    @GetMapping(path = "/{id}")
    public ProjectTaskDtoResponse getById(@PathVariable Long id) {
        return mapProjectTaskEntityToDtoResponse(projectTaskService.findById(id));
    }

    @GetMapping
    public List<ProjectTaskDtoResponse> getAll() {
        List<ProjectTaskDtoResponse> result = new ArrayList<>();
        projectTaskService.findAll().forEach(projectTask ->
                result.add(mapProjectTaskEntityToDtoResponse(projectTask)));
        return result;
    }

    @GetMapping(path = "/project-id")
    public List<ProjectTaskDtoResponse> getProjectTaskByProjectId(@RequestParam("projectId") Long projectId) {
        List<ProjectTask> projectTasks = projectTaskService.findAllProjectTasksByProjectId(projectId);
        List<ProjectTaskDtoResponse> projectTaskDtoResponses = mapProjectTaskListToDtoResponseList(projectTasks);
        return projectTaskDtoResponses;
    }

    @PutMapping(path = "/{id}")
    public ProjectTaskDtoResponse update(@PathVariable (value = "id") Long id, @RequestBody ProjectTaskDtoRequest projectTaskDtoRequest) {
        ProjectTask projectTask =  projectTaskService.update( id, projectTaskDtoRequest);
        return mapProjectTaskEntityToDtoResponse(projectTask);
    }

    @DeleteMapping(path = "/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        projectTaskService.delete(id);
        return HttpStatus.OK;
    }

    private List<ProjectTaskDtoResponse> mapProjectTaskListToDtoResponseList(List<ProjectTask> projectTasks) {
        List<ProjectTaskDtoResponse> projectTaskDtoResponses = new ArrayList<>();
        projectTasks.forEach(projectTask -> {
            projectTaskDtoResponses.add(mapProjectTaskEntityToDtoResponse(projectTask));
        });
        return projectTaskDtoResponses;
    }

    // /{filtering} e inlocuit fie de "completed", fie de "uncompleted"
//    @GetMapping(path = "/{filtering}")
//    public List<ProjectTaskDtoResponse> getCompletedProjectTasksByProjectId(@PathVariable("filtering") String filtering, @RequestParam("projectId") Long projectId) {
//        List<ProjectTask> projectTasks = new ArrayList<>();
//        if (filtering.equals("completed")) {
//            projectTasks = projectTaskService.findAllCompletedProjectTasksByProjectId(projectId);
//        } else if (filtering.equals("uncompleted")){
//            projectTasks = projectTaskService.findProjectTasksByProjectIdWhichStartInFuture(projectId);
//        }
//        return mapProjectTaskListToDtoResponseList(projectTasks);
//    }


    private ProjectTask mapProjectTaskDtoRequestToProjectTask(ProjectTaskDtoRequest projectTaskDtoRequest) {
        ProjectTask projectTask = new ProjectTask();
        projectTask.setName(projectTaskDtoRequest.getName());
        projectTask.setStartDate(projectTaskDtoRequest.getStartDate());
        projectTask.setEndDate(projectTaskDtoRequest.getEndDate());
        Project project = projectService.findById(projectTaskDtoRequest.getProjectId());
        projectTask.setProject(project);
//        List<ProjectSubTask> projectSubTasksList = projectSubtaskService
//                .findByIds(projectTaskDtoRequest.getSubtasksIds());
//        projectTask.setProjectSubTasks(projectSubTasksList);
        return projectTask;
    }

    private ProjectTaskDtoResponse mapProjectTaskEntityToDtoResponse(ProjectTask projectTask) {
        ProjectTaskDtoResponse dtoResponse = new ProjectTaskDtoResponse();

        dtoResponse.setId(projectTask.getId());
        dtoResponse.setName(projectTask.getName());
        dtoResponse.setStartDate(projectTask.getStartDate());
        dtoResponse.setEndDate(projectTask.getEndDate());
        dtoResponse.setProjectDto(mapProjectTaskEntityToProjectTaskDto(projectTask.getProject()));
//        List<ProjectTaskToProjectSubtaskDtoResponse> projectTaskToProjectSubtaskDtoResponses = new ArrayList<>();
//        projectTask.getProjectSubTasks().forEach(projectSubTask -> {
//            projectTaskToProjectSubtaskDtoResponses
//                    .add(mapProjectTaskEntityToProjectSubtaskDtoResponse(projectSubTask));
//        });
//        dtoResponse.setProjectSubtasksDto(projectTaskToProjectSubtaskDtoResponses);
        return dtoResponse;
    }

    private ProjectTaskToProjectDtoResponse mapProjectTaskEntityToProjectTaskDto(Project project) {
        ProjectTaskToProjectDtoResponse dtoResponse = new ProjectTaskToProjectDtoResponse();
        dtoResponse.setName(project.getName());
        dtoResponse.setId(project.getId());
        return dtoResponse;
    }

    private ProjectTaskToProjectSubtaskDtoResponse mapProjectTaskEntityToProjectSubtaskDtoResponse
            (ProjectSubTask projectSubTask) {
        ProjectTaskToProjectSubtaskDtoResponse dtoResponse = new ProjectTaskToProjectSubtaskDtoResponse();
        dtoResponse.setSubTaskStatus(projectSubTask.getStatus());
        dtoResponse.setName(projectSubTask.getName());
        dtoResponse.setId(projectSubTask.getId());
        dtoResponse.setAssignedUser(projectSubTask.getAssignedUser().getUsername());
        return dtoResponse;
    }

}
