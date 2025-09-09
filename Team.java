package com.project_management_system.model;

public class Team {
    private int teamId;
    private String teamName;
    private String description;

    public Team(int teamId, String teamName, String description) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.description = description;
    }

    // Getters
    public int getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Team{" +
               "teamId=" + teamId +
               ", teamName=\'" + teamName + '\'' +
               ", description=\'" + description + '\'' +
               '\'' +
               '}';
    }
}


