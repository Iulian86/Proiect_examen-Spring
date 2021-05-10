package com.aob.spring.securityRegistration.repository.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "projects")

public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String location;

    @DateTimeFormat
    private LocalDate registeredOn = LocalDate.now();

    @DateTimeFormat
    private LocalDate startDate;

    @DateTimeFormat
    private LocalDate finishedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "project_users", joinColumns = @JoinColumn(name = "project_id"),
                                      inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> assignedUsers = new HashSet<>();

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL
    )
    private List<ProjectTask> projectTasks = new ArrayList<>();

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL
    )
    private List<ProjectSubTask> projectSubtasks = new ArrayList<>();

    public Project() {
    }

    public Project( String name, String location, LocalDate registeredAt,
                   LocalDate startDate,LocalDate finishedDate, User user) {
//        this.id = id;
        this.name = name;
        this.location =location;
        this.registeredOn = registeredAt;
        this.startDate = startDate;
        this.finishedDate = finishedDate;
        this.user = user;
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

    public void setAdministrator(User user) {
        this.user = user;
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

    public void setRegisteredOn(LocalDate registeredAt) {
        this.registeredOn = registeredAt;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(Set<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public List<ProjectTask> getProjectTasks() {
        return projectTasks;
    }

    public void setProjectTasks(List<ProjectTask> projectTasks) {
        this.projectTasks = projectTasks;
    }

    public List<ProjectSubTask> getProjectSubtasks() {
        return projectSubtasks;
    }

    public void setProjectSubtasks(List<ProjectSubTask> projectSubtasks) {
        this.projectSubtasks = projectSubtasks;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectName='" + name + '\'' +
                '}';
    }

}
