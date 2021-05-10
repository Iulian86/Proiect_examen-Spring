package com.aob.spring.securityRegistration.repository.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ProjectSubTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @DateTimeFormat
    private LocalDate startDate;

    @DateTimeFormat
    private LocalDate targetDate;

//    private Integer duration = startDate.getDayOfYear()-targetDate.getDayOfYear();

    @DateTimeFormat
    private LocalDate completeDate;

    @Enumerated(value = EnumType.STRING)
    private SubTaskStatus status;

//    @Min(value = 0, message = "The weight value should be above >= 0")
//    @Max(value = 100, message = "The weight value should be less <= 100")
    private int statusPercentage = 0;

    @Min(value = 1, message = "The weight value should be above >= 1")
    @Max(value = 10, message = "The weight value should be less <= 10")
    private int weight;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignedUser;

    @ManyToMany
    @JoinTable(name = "subtasks_users", joinColumns = @JoinColumn(name = "subtask_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> assignedUsers = new HashSet<>();

    @ManyToOne
    private ProjectTask projectTask;

    @ManyToOne
    private Project project;

    public ProjectSubTask() {
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

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

//    public Integer getDuration() {
//        return duration;
//    }
//
//    public void setDuration(Integer duration) {
//        this.duration = duration;
//    }

    public LocalDate getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(LocalDate completeDate) {
        this.completeDate = completeDate;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public Set<User> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(Set<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public ProjectTask getProjectTask() {
        return projectTask;
    }

    public void setProjectTask(ProjectTask projectTask) {
        this.projectTask = projectTask;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
