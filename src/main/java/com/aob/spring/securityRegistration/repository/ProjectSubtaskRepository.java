package com.aob.spring.securityRegistration.repository;

import com.aob.spring.securityRegistration.repository.model.Project;
import com.aob.spring.securityRegistration.repository.model.ProjectSubTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectSubtaskRepository extends JpaRepository<ProjectSubTask, Long> {
    List<ProjectSubTask> findByNameContainsAndProjectIdAndProjectTaskNull(String s, Long projectId);
    List<ProjectSubTask> findAllByProjectId(Long projectId);
    List<ProjectSubTask> findAllByProjectTaskId(Long projectTaskId);
    ProjectSubTask findByName(String subtaskName);

//    List<ProjectSubTask> findAllByProjectIdAndProjectTaskId(Long projectId, Long projectTaskId);

}
