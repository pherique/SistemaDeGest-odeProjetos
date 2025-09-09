package com.project_management_system.controller;

import com.project_management_system.dao.TeamDAO;
import com.project_management_system.model.Team;
import com.project_management_system.model.User;

import java.sql.SQLException;
import java.util.List;

public class TeamController {
    private TeamDAO teamDAO;

    public TeamController() {
        this.teamDAO = new TeamDAO();
    }

    public void createTeam(Team team) throws SQLException {
        teamDAO.addTeam(team);
    }

    public Team getTeamById(int teamId) throws SQLException {
        return teamDAO.getTeamById(teamId);
    }

    public List<Team> getAllTeams() throws SQLException {
        return teamDAO.getAllTeams();
    }

    public void updateTeam(Team team) throws SQLException {
        teamDAO.updateTeam(team);
    }

    public void deleteTeam(int teamId) throws SQLException {
        teamDAO.deleteTeam(teamId);
    }

    public void addMemberToTeam(int teamId, int userId) throws SQLException {
        teamDAO.addTeamMember(teamId, userId);
    }

    public void removeMemberFromTeam(int teamId, int userId) throws SQLException {
        teamDAO.removeTeamMember(teamId, userId);
    }

    public List<User> getTeamMembers(int teamId) throws SQLException {
        return teamDAO.getTeamMembers(teamId);
    }
}


