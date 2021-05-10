package com.aob.spring.securityRegistration.dto;

import com.aob.spring.securityRegistration.repository.model.SubTaskStatus;

public class ProjectTaskToProjectSubtaskDtoResponse {
    private Long id;
    private SubTaskStatus subTaskStatus;
    private String assignedUser;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubTaskStatus getSubTaskStatus() {
        return subTaskStatus;
    }

    public void setSubTaskStatus(SubTaskStatus subTaskStatus) {
        this.subTaskStatus = subTaskStatus;
    }

    public String getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
