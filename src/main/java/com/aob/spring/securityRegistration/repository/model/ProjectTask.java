package com.aob.spring.securityRegistration.repository.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @DateTimeFormat
    private LocalDate startDate;

    @DateTimeFormat
    private LocalDate endDate;

    private SubTaskStatus status;

    @ManyToOne
    @JoinColumn (name = "project_id")
    private Project project;

    @OneToMany(
            mappedBy = "projectTask",
            cascade = CascadeType.ALL
    )
    private List<ProjectSubTask> projectSubTasks = new ArrayList<>();

    public ProjectTask() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public SubTaskStatus getStatus() {
        return status;
    }

    public void setStatus(SubTaskStatus status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<ProjectSubTask> getProjectSubTasks() {
        return projectSubTasks;
    }

    public void setProjectSubTasks(List<ProjectSubTask> projectSubTasks) {
        this.projectSubTasks = projectSubTasks;
    }

    public void setProjectSubTask(ProjectSubTask projectSubTask) {
        this.projectSubTasks.add(projectSubTask);
    }
}
