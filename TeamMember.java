package com.project_management_system.model;

public class TeamMember {
    private int teamMemberId;
    private int teamId;
    private int userId;

    public TeamMember(int teamMemberId, int teamId, int userId) {
        this.teamMemberId = teamMemberId;
        this.teamId = teamId;
        this.userId = userId;
    }

    // Getters
    public int getTeamMemberId() {
        return teamMemberId;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getUserId() {
        return userId;
    }

    // Setters
    public void setTeamMemberId(int teamMemberId) {
        this.teamMemberId = teamMemberId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "TeamMember{" +
               "teamMemberId=" + teamMemberId +
               ", teamId=" + teamId +
               ", userId=" + userId +
               "}";
    }
}


