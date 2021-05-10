package com.aob.spring.securityRegistration.dto;

import com.aob.spring.securityRegistration.repository.model.ProjectTask;
import com.aob.spring.securityRegistration.repository.model.SubTaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ProjectSubtaskDtoRequest {
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;

    private SubTaskStatus status;
    private int statusPercentage;
    private int weight;
    private String username;
//    private Long projectId;
    private Long projectTaskId;


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

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public SubTaskStatus getStatus() {
        return status;
    }

    public void setStatus(SubTaskStatus status) {
        this.status = status;
    }

    public int getStatusPercentage() {
        return statusPercentage;
    }

    public void setStatusPercentage(int statusPercentage) {
        this.statusPercentage = statusPercentage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getProjectTaskId() {
        return projectTaskId;
    }

    public void setProjectTaskId(Long projectTaskId) {
        this.projectTaskId = projectTaskId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

//    public Long getProjectId() {
//        return projectId;
//    }
//
//    public void setProjectId(Long projectId) {
//        this.projectId = projectId;
//    }


}
