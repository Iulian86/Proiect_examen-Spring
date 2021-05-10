package com.aob.spring.securityRegistration.repository;

import com.aob.spring.securityRegistration.repository.model.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {

    @Query("SELECT projectTask from ProjectTask projectTask WHERE projectTask.startDate <= :endDate and projectTask.endDate >= :startDate and projectTask.project.id = :projectId")
    List<ProjectTask> getProjectTasksInIntervalAndWithProjectId(LocalDate startDate, LocalDate endDate, Long projectId);

    @Query("SELECT projectTask from ProjectTask projectTask WHERE projectTask.endDate <= :currentDate and projectTask.project.id = :projectId")
    List<ProjectTask> getProjectTaskCompletedByProjectId(LocalDate currentDate, Long projectId);

    @Query("SELECT projectTask from ProjectTask projectTask WHERE projectTask.startDate > :currentDate and projectTask.project.id = :projectId")
    List<ProjectTask> getProjectTasksNotStartedByProjectId(LocalDate currentDate, Long projectId);

    List<ProjectTask> findProjectTaskByProjectId(Long id);
}
