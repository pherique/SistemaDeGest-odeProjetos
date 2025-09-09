package com.project_management_system.model;

import java.time.LocalDate;

public class Project {
    private int projectId;
    private String projectName;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String status;
    private int managerId;

    public Project(int projectId, String projectName, String description, LocalDate startDate, LocalDate dueDate, String status, int managerId) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.status = status;
        this.managerId = managerId;
    }

    // Getters
    public int getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }

    public int getManagerId() {
        return managerId;
    }

    // Setters
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return projectName;
    }
}


