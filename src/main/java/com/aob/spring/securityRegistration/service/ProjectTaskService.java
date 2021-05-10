package com.aob.spring.securityRegistration.service;
import com.aob.spring.securityRegistration.dto.ProjectDtoRequest;
import com.aob.spring.securityRegistration.dto.ProjectTaskDtoRequest;
import com.aob.spring.securityRegistration.repository.model.Project;
import com.aob.spring.securityRegistration.repository.model.ProjectTask;

import java.util.List;

public interface ProjectTaskService {

    ProjectTask save(ProjectTask agileSprint);
    ProjectTask findById(Long id);
    List<ProjectTask> findAll();
    List<ProjectTask> findAllProjectTasksByProjectId(Long projectId);
    List<ProjectTask> findAllCompletedProjectTasksByProjectId(Long projectId);
    List<ProjectTask> findProjectTasksByProjectIdWhichStartInFuture(Long projectId);
    ProjectTask update(Long id, ProjectTaskDtoRequest projectTask);
    void delete(Long id);
}
