package com.project_management_system.model;

import java.time.LocalDate;

public class Task {
    private int taskId;
    private String title;
    private String description;
    private int projectId;
    private int assignedToUserId;
    private String status;
    private LocalDate plannedStartDate;
    private LocalDate plannedEndDate;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;

    public Task(int taskId, String title, String description, int projectId, int assignedToUserId, String status, LocalDate plannedStartDate, LocalDate plannedEndDate, LocalDate actualStartDate, LocalDate actualEndDate) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.projectId = projectId;
        this.assignedToUserId = assignedToUserId;
        this.status = status;
        this.plannedStartDate = plannedStartDate;
        this.plannedEndDate = plannedEndDate;
        this.actualStartDate = actualStartDate;
        this.actualEndDate = actualEndDate;
    }

    // Getters
    public int getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getProjectId() {
        return projectId;
    }

    public int getAssignedToUserId() {
        return assignedToUserId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getPlannedStartDate() {
        return plannedStartDate;
    }

    public LocalDate getPlannedEndDate() {
        return plannedEndDate;
    }

    public LocalDate getActualStartDate() {
        return actualStartDate;
    }

    public LocalDate getActualEndDate() {
        return actualEndDate;
    }

    // Setters
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setAssignedToUserId(int assignedToUserId) {
        this.assignedToUserId = assignedToUserId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPlannedStartDate(LocalDate plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
    }

    public void setPlannedEndDate(LocalDate plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public void setActualStartDate(LocalDate actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public void setActualEndDate(LocalDate actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    @Override
    public String toString() {
        return "Task{" +
               "taskId=" + taskId +
               ", title=\'" + title + '\'' +
               ", description=\'" + description + '\'' +
               ", projectId=" + projectId +
               ", assignedToUserId=" + assignedToUserId +
               ", status=\'" + status + '\'' +
               ", plannedStartDate=" + plannedStartDate +
               ", plannedEndDate=" + plannedEndDate +
               ", actualStartDate=" + actualStartDate +
               ", actualEndDate=" + actualEndDate +
               '}'
               ;
    }
}


