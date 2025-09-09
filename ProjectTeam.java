package com.project_management_system.model;

public class ProjectTeam {
    private int projectTeamId;
    private int projectId;
    private int teamId;

    public ProjectTeam(int projectTeamId, int projectId, int teamId) {
        this.projectTeamId = projectTeamId;
        this.projectId = projectId;
        this.teamId = teamId;
    }

    // Getters
    public int getProjectTeamId() {
        return projectTeamId;
    }

    public int getProjectId() {
        return projectId;
    }

    public int getTeamId() {
        return teamId;
    }

    // Setters
    public void setProjectTeamId(int projectTeamId) {
        this.projectTeamId = projectTeamId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "ProjectTeam{" +
               "projectTeamId=" + projectTeamId +
               ", projectId=" + projectId +
               ", teamId=" + teamId +
               "}";
    }
}


