package com.aob.spring.securityRegistration.service.implementation;

import com.aob.spring.securityRegistration.dto.ProjectSubtaskDtoRequest;
import com.aob.spring.securityRegistration.repository.*;
import com.aob.spring.securityRegistration.repository.model.*;
import com.aob.spring.securityRegistration.service.EmailService;
import com.aob.spring.securityRegistration.service.ProjectSubtaskService;
import com.aob.spring.securityRegistration.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectSubtaskServiceImplementation implements ProjectSubtaskService {

    private ProjectSubtaskRepository projectSubtaskRepository;
    private ProjectTaskRepository projectTaskRepository;
    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    private EmailService emailService;

    public ProjectSubtaskServiceImplementation(@Autowired ProjectSubtaskRepository projectSubtaskRepository,
                                               @Autowired ProjectTaskRepository projectTaskRepository,
                                               @Autowired ProjectRepository projectRepository,
                                               @Autowired UserRepository userRepository,
                                               @Autowired EmailService emailService) {
        this.projectSubtaskRepository = projectSubtaskRepository;
        this.projectTaskRepository = projectTaskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public ProjectSubTask save(ProjectSubTask projectSubTask) {
        return projectSubtaskRepository.save(projectSubTask);
    }

    @Override
    public ProjectSubTask findById(Long id) {
        Optional<ProjectSubTask> projectSubTask = projectSubtaskRepository.findById(id);
        if (projectSubTask.isPresent()) {
           return projectSubTask.get();
        } else {
            throw new ResourceNotFoundException(String.format("Resource of type %s with id %d  was not found", "projectSubTask", id));
        }
    }

    @Override
    public List<ProjectSubTask> findAll() {
        return projectSubtaskRepository.findAll();
    }

//    @Override
//    public List<ProjectSubTask> findByIds(List<Long> ids) {
//        List<ProjectSubTask> projectSubTasks = new ArrayList<>();
//        ids.forEach(id -> projectSubTasks.add(findById(id)));
//        return projectSubTasks;
//    }

    @Override
    public List<ProjectSubTask> findAllByProjectId(Long projectId) {
        return projectSubtaskRepository.findAllByProjectId(projectId);
    }

    @Override
    public List<ProjectSubTask> findAllByProjectTaskId(Long projectTaskId) {
        return projectSubtaskRepository.findAllByProjectTaskId(projectTaskId);
    }

//    @Override
//    public List<ProjectSubTask> findAllProjectSubtasksByProjectTaskId(Long projectTaskId) {
//        Optional<ProjectTask> projectTask = projectTaskRepository.findById(projectTaskId);
//        if (!projectTask.isPresent()) {
//            throw new ResourceNotFoundException(String.format("Project task with id %d not found", projectTaskId));
//        }
//        return projectSubtaskRepository.findAllByProjectTaskId(projectTaskId);
//    }

    @Override
    public List<ProjectSubTask> findByNameContainsAndProjectId(String s, Long projectId) {
        return projectSubtaskRepository.findByNameContainsAndProjectIdAndProjectTaskNull(s, projectId);
    }


//    @Override
//    public List<ProjectSubTask> findAllProjectSubtasksByProjectIdAndProjectTaskId(Long projectId, Long projectTaskId) {
//        Optional<Project> project = projectRepository.findById(projectId);
//        if (!project.isPresent()) {
//            throw new ResourceNotFoundException(String.format("Project with id %d not found", projectId));
//        }
//        Optional<ProjectTask> projectTask = projectTaskRepository.findById(projectTaskId);
//        if (!projectTask.isPresent()) {
//            throw new ResourceNotFoundException(String.format("Project task with id %d not found", projectTaskId));
//        }
//        return projectSubtaskRepository.findAllByProjectIdAndProjectTaskId(projectId, projectTaskId);
//    }

    //    @Override
//    public List<ProjectSubTask> findByNameContainsAndProjectTaskId(String s, Long projectTaskId) {
//        return projectSubtaskRepository.findByNameContainsAndProjectTaskId(s, projectTaskId);
//    }

//    @Override
//    public List<ProjectSubTask> findAllByProjectTaskId(Long projectId) {
//        return projectSubtaskRepository.findAllByProjectTaskId(projectId);
//    }


//    @Override
//    public ProjectSubTask assignProjectSubtaskToUser(ProjectSubTask projectSubTask, String username) {
//        User user = userRepository.findByUsername(username);
//        if (user != null) {
//            projectSubTask.setAssignedUser(user);
//            return projectSubtaskRepository.save(projectSubTask);
//        } else {
//            throw new ResourceNotFoundException(String.format("Resource of type %s with id %s  was not found", "user", username));
//        }
//    }

    @Override
    public ProjectSubTask assignUserToSubtask(String username, String subtaskName) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException(String.format("Resource %s with id %s not found", "user", username));
        }
        ProjectSubTask projectSubTask = projectSubtaskRepository.findByName(subtaskName);
        if (projectSubTask == null) {
            throw new ResourceNotFoundException(String.format("Resource %s with id %s not found", "projectSubTask", subtaskName));
        }
        Set<User> assignedUsers = projectSubTask.getAssignedUsers();
        assignedUsers.add(user);
        projectSubTask.setAssignedUsers(assignedUsers);
        ProjectSubTask updatedProjectSubtask = projectSubtaskRepository.save(projectSubTask);
//        emailService.sendEmail(user.getEmail(), String.format("You have been assigned to project with name %s",
//                project.getName()));
        return updatedProjectSubtask;
    }

    @Override
    public void sendInviteLinkToUserForSubtaskAssignment(String username, String subtaskName) {
        if (isUserAndSubtaskPresent(username, subtaskName)) {
            User user = userRepository.findByUsername(username);
            // http://localhost:8081/projects/users?projectName=projectName&username=username;
            emailService
                    .sendEmail(user.getEmail(),
                            String.format("You have been invited to the subtask. Please " +
                                            "access the link to be assigned. " +
                                            "http://localhost:9191/projects/users?username=%s&projectName=%s",
                                    username, subtaskName));
        }
    }

    private boolean isUserAndSubtaskPresent(String username, String subtaskName) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException(String.format("Resource %s with id %s not found", "user", username));
        }
        ProjectSubTask projectSubTask = projectSubtaskRepository.findByName(subtaskName);
        if (projectSubTask == null) {
            throw new ResourceNotFoundException(String.format("Resource %s with id %s not found", "project", subtaskName));
        }
        return true;
    }


    @Override
    public ProjectSubTask updateStatus(Long id, SubTaskStatus newStatus) {
        ProjectSubTask projectSubTask = findById(id);
        projectSubTask.setStatus(newStatus);
        return projectSubtaskRepository.save(projectSubTask);
    }

    @Override
    public ProjectSubTask update(Long id, ProjectSubtaskDtoRequest updatedSubtask) {
        Optional<ProjectSubTask> projectSubTask = projectSubtaskRepository.findById(id);
        if (projectSubTask.isPresent()) {
            ProjectSubTask projectSubtaskInDatabase = projectSubTask.get();
            if (updatedSubtask.getName() != null) {
                projectSubtaskInDatabase.setName(updatedSubtask.getName());
            }
            if (updatedSubtask.getStartDate() != null) {
                projectSubtaskInDatabase.setStartDate(updatedSubtask.getStartDate());
            }
            if (updatedSubtask.getTargetDate() != null) {
                projectSubtaskInDatabase.setTargetDate(updatedSubtask.getTargetDate());
            }
//            if (updatedSubtask.getStatus() != null) {
//                projectSubtaskInDatabase.setStatus(updatedSubtask.getStatus());
//            }
            if (updatedSubtask.getWeight() != 0) {
                projectSubtaskInDatabase.setWeight(updatedSubtask.getWeight());
            }
            return projectSubtaskRepository.save(projectSubtaskInDatabase);
        } else {
            throw new ResourceNotFoundException(String.format("Resource %s with id %d not found", "projectSubTask", id));
        }
    }

    @Override
    public void delete(Long id) {
        Optional<ProjectSubTask> projectSubTask = projectSubtaskRepository.findById(id);
        if (projectSubTask.isPresent()) {
            projectSubtaskRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(String.format("Resource %s with id %d not found", "projectSubtask", id));
        }
    }
}
