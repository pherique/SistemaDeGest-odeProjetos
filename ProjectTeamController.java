package com.project_management_system.controller;

import com.project_management_system.dao.ProjectTeamDAO;
import com.project_management_system.model.Project;
import com.project_management_system.model.Team;

import java.sql.SQLException;
import java.util.List;

public class ProjectTeamController {
    private ProjectTeamDAO projectTeamDAO;

    public ProjectTeamController() {
        this.projectTeamDAO = new ProjectTeamDAO();
    }

    public void addTeamToProject(int projectId, int teamId) throws SQLException {
        projectTeamDAO.addProjectTeam(projectId, teamId);
    }

    public void removeTeamFromProject(int projectId, int teamId) throws SQLException {
        projectTeamDAO.removeProjectTeam(projectId, teamId);
    }

    public List<Team> getTeamsByProjectId(int projectId) throws SQLException {
        return projectTeamDAO.getTeamsByProjectId(projectId);
    }

    public List<Project> getProjectsByTeamId(int teamId) throws SQLException {
        return projectTeamDAO.getProjectsByTeamId(teamId);
    }
}


