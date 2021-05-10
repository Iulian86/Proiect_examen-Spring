package com.aob.spring.securityRegistration.dto;
import com.aob.spring.securityRegistration.repository.model.SubTaskStatus;

import java.time.LocalDate;
import java.util.List;

public class ProjectSubtaskDtoResponse {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate targetDate;
    private SubTaskStatus status;
    private int statusPercentage;
    private int weight;
//    private ProjectSubtaskToProjectDtoResponse projectDto;
    private ProjectSubtaskToProjectTaskDtoResponse projectTaskDto;
    private MinimumUserInfoDto userInfo;
    private List<MinimumUserInfoDto> assignedUsers;

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

//    public ProjectSubtaskToProjectDtoResponse getProjectDto() {
//        return projectDto;
//    }
//
//    public void setProjectDto(ProjectSubtaskToProjectDtoResponse projectDto) {
//        this.projectDto = projectDto;
//    }

    public ProjectSubtaskToProjectTaskDtoResponse getProjectTaskDto() {
        return projectTaskDto;
    }

    public void setProjectTaskDto(ProjectSubtaskToProjectTaskDtoResponse projectTaskDto) {
        this.projectTaskDto = projectTaskDto;
    }

    public MinimumUserInfoDto getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(MinimumUserInfoDto userInfo) {
        this.userInfo = userInfo;
    }

    public List<MinimumUserInfoDto> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<MinimumUserInfoDto> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
