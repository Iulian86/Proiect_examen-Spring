package com.aob.spring.securityRegistration.dto;

import java.time.LocalDate;
import java.util.List;

public class ProjectTaskDtoResponse {

    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectTaskToProjectDtoResponse projectDto;
    private List<ProjectTaskToProjectSubtaskDtoResponse> projectSubtasksDto;

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

    public ProjectTaskToProjectDtoResponse getProjectDto() {
        return projectDto;
    }

    public void setProjectDto(ProjectTaskToProjectDtoResponse projectDto) {
        this.projectDto = projectDto;
    }

    public List<ProjectTaskToProjectSubtaskDtoResponse> getProjectSubtasksDto() {
        return projectSubtasksDto;
    }

    public void setProjectSubtasksDto(List<ProjectTaskToProjectSubtaskDtoResponse> projectSubtasksDto) {
        this.projectSubtasksDto = projectSubtasksDto;
    }
}
