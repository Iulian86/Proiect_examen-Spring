package com.aob.spring.securityRegistration.service;



import com.aob.spring.securityRegistration.dto.ProjectSubtaskDtoRequest;
import com.aob.spring.securityRegistration.repository.model.Project;
import com.aob.spring.securityRegistration.repository.model.ProjectSubTask;
import com.aob.spring.securityRegistration.repository.model.SubTaskStatus;

import java.util.List;

public interface ProjectSubtaskService {

    ProjectSubTask save(ProjectSubTask projectSubTask);
    ProjectSubTask findById(Long id);
    List<ProjectSubTask> findAll();
    List<ProjectSubTask> findAllByProjectId(Long projectId);
    List<ProjectSubTask> findAllByProjectTaskId(Long projectTaskId);
//    ProjectSubTask assignProjectSubtaskToUser(ProjectSubTask projectSubTask, String username);
    ProjectSubTask assignUserToSubtask(String username, String subtaskName);
    void sendInviteLinkToUserForSubtaskAssignment(String username, String subtaskName);
    List<ProjectSubTask> findByNameContainsAndProjectId(String name, Long projectId);
    //    List<ProjectSubTask> findByIds(List<Long> ids);
    //    List<ProjectSubTask> findAllProjectSubtasksByProjectTaskId(Long projectTaskId);
    //    List<ProjectSubTask> findAllProjectSubtasksByProjectIdAndProjectTaskId(Long projectId, Long projectTaskId);
    //    List<ProjectSubTask> findByNameContainsAndProjectTaskId(String name, Long projectTaskId);
    ProjectSubTask update(Long id, ProjectSubtaskDtoRequest updatedSubtask);
    ProjectSubTask updateStatus(Long id, SubTaskStatus newStatus);
    void delete(Long id);

}
