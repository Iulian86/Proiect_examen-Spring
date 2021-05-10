package com.aob.spring.securityRegistration.dto;

import java.time.LocalDate;
import java.util.List;

public class ProjectDtoResponse {
    private Long id;
    private String name;
    private String location;
    private LocalDate registeredOn;
    private LocalDate startDate;
    private LocalDate finishedDate;
    private MinimumUserInfoDto administrator;
    private List<MinimumUserInfoDto> assignedUsers;
//    private int sprintNumbers;

    public ProjectDtoResponse(Long id, String name, String location, LocalDate registeredOn,
                              LocalDate startDate, LocalDate finishedDate, MinimumUserInfoDto administrator,
                              List<MinimumUserInfoDto> assignedUsers) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.registeredOn = registeredOn;
        this.startDate = startDate;
        this.finishedDate = finishedDate;
        this.administrator = administrator;
        this.assignedUsers = assignedUsers;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(LocalDate finishedDate) {
        this.finishedDate = finishedDate;
    }

    public com.aob.spring.securityRegistration.dto.MinimumUserInfoDto getAdministrator() {
        return administrator;
    }

    public void setAdministrator(com.aob.spring.securityRegistration.dto.MinimumUserInfoDto administrator) {
        this.administrator = administrator;
    }

    public List<com.aob.spring.securityRegistration.dto.MinimumUserInfoDto> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<com.aob.spring.securityRegistration.dto.MinimumUserInfoDto> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

}
