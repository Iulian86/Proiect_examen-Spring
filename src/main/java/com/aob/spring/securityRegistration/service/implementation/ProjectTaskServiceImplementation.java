package com.aob.spring.securityRegistration.service.implementation;

import com.aob.spring.securityRegistration.dto.ProjectTaskDtoRequest;
import com.aob.spring.securityRegistration.repository.*;
import com.aob.spring.securityRegistration.repository.model.*;
import com.aob.spring.securityRegistration.service.ProjectTaskService;
import com.aob.spring.securityRegistration.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectTaskServiceImplementation implements ProjectTaskService {

    private ProjectTaskRepository projectTaskRepository;
    private ProjectSubtaskRepository projectSubtaskRepository;
    private ProjectRepository projectRepository;

    public ProjectTaskServiceImplementation(@Autowired ProjectTaskRepository projectTaskRepository,
                                            @Autowired ProjectSubtaskRepository projectSubtaskRepository,
                                            @Autowired ProjectRepository projectRepository) {
        this.projectTaskRepository = projectTaskRepository;
        this.projectSubtaskRepository = projectSubtaskRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectTask save(ProjectTask projectTask) {
        List<ProjectTask> projectTasks = projectTaskRepository.getProjectTasksInIntervalAndWithProjectId
                (projectTask.getStartDate(), projectTask.getEndDate(), projectTask.getProject().getId());
//        if (projectTasks.size() >= 1) {
//            throw new OverlappingSprintException("There is already a task within the interval");
//        }
        return projectTaskRepository.save(projectTask);
    }


    @Override
    public ProjectTask findById(Long id) {
        Optional<ProjectTask> projectTask = projectTaskRepository.findById(id);
        if (projectTask.isPresent()) {
            return projectTask.get();
        } else {
            throw new ResourceNotFoundException(String.format("Resource %s with name %d not found", "projectTask", id));
        }
    }

    @Override
    public List<ProjectTask> findAll() {
        return projectTaskRepository.findAll();
    }

    @Override
    public List<ProjectTask> findAllProjectTasksByProjectId(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (!project.isPresent()) {
            throw new ResourceNotFoundException(String.format("Project with id %d not found", projectId));
        }
        return projectTaskRepository.findProjectTaskByProjectId(projectId);
    }

    @Override
    public List<ProjectTask> findAllCompletedProjectTasksByProjectId(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (!project.isPresent()) {
            throw new ResourceNotFoundException(String.format("Project with id %d not found", projectId));
        }
        return projectTaskRepository.getProjectTaskCompletedByProjectId(LocalDate.now(), projectId);
    }

    @Override
    public List<ProjectTask> findProjectTasksByProjectIdWhichStartInFuture(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (!project.isPresent()) {
            throw new ResourceNotFoundException(String.format("Project with id %d not found", projectId));
        }
        return projectTaskRepository.getProjectTasksNotStartedByProjectId(LocalDate.now(), projectId);
    }

    @Override
    public ProjectTask update(Long id, ProjectTaskDtoRequest updatedProjectTask) {
        Optional<ProjectTask> projectTask = projectTaskRepository.findById(id);
        if (projectTask.isPresent()) {
            ProjectTask projectTaskInDatabase = projectTask.get();
            if (updatedProjectTask.getName() != null) {
                projectTaskInDatabase.setName(updatedProjectTask.getName());
            }
            if (updatedProjectTask.getStartDate() != null) {
                projectTaskInDatabase.setStartDate(updatedProjectTask.getStartDate());
            }
            if (updatedProjectTask.getEndDate() != null) {
                projectTaskInDatabase.setEndDate(updatedProjectTask.getEndDate());
            }
            return projectTaskRepository.save(projectTaskInDatabase);
        } else {
            throw new ResourceNotFoundException(String.format("Resource %s with id %d not found", "projectTask", id));
        }
    }

    @Override
    public void delete(Long id) {
        Optional<ProjectTask> optionalProjectTask = projectTaskRepository.findById(id);
        if (!optionalProjectTask.isPresent()) {
            throw new ResourceNotFoundException(String.format("Project task with id %d not found", id));
        }
        projectTaskRepository.deleteById(id);
    }
}
