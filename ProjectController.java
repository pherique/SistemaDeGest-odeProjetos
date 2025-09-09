package com.project_management_system.controller;

import com.project_management_system.dao.ProjectDAO;
import com.project_management_system.model.Project;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ProjectController {
    private ProjectDAO projectDAO;

    public ProjectController() {
        this.projectDAO = new ProjectDAO();
    }

    public void createProject(Project project) throws SQLException {
        projectDAO.addProject(project);
    }

    public Project getProjectById(int projectId) throws SQLException {
        return projectDAO.getProjectById(projectId);
    }

    public List<Project> getAllProjects() throws SQLException {
        return projectDAO.getAllProjects();
    }

    public void updateProject(Project project) throws SQLException {
        projectDAO.updateProject(project);
    }

    public void deleteProject(int projectId) throws SQLException {
        projectDAO.deleteProject(projectId);
    }

    public List<Project> getProjectsAtRisk() throws SQLException {
        List<Project> allProjects = projectDAO.getAllProjects();
        // Filter projects where current date > due date and status is not 'concluido' or 'cancelado'
        LocalDate today = LocalDate.now();
        allProjects.removeIf(project ->
                (project.getDueDate().isAfter(today) || project.getDueDate().isEqual(today)) ||
                project.getStatus().equals("concluido") ||
                project.getStatus().equals("cancelado")
        );
        return allProjects;
    }
}


