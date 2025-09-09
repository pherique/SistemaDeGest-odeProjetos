package com.project_management_system.dao;

import com.project_management_system.model.Project;
import com.project_management_system.model.Team;
import com.project_management_system.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectTeamDAO {

    public void addProjectTeam(int projectId, int teamId) throws SQLException {
        String sql = "INSERT INTO project_teams (project_id, team_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            stmt.setInt(2, teamId);
            stmt.executeUpdate();
        }
    }

    public void removeProjectTeam(int projectId, int teamId) throws SQLException {
        String sql = "DELETE FROM project_teams WHERE project_id = ? AND team_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            stmt.setInt(2, teamId);
            stmt.executeUpdate();
        }
    }

    public List<Team> getTeamsByProjectId(int projectId) throws SQLException {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT t.* FROM teams t JOIN project_teams pt ON t.team_id = pt.team_id WHERE pt.project_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    teams.add(new Team(
                            rs.getInt("team_id"),
                            rs.getString("team_name"),
                            rs.getString("description")
                    ));
                }
            }
        }
        return teams;
    }

    public List<Project> getProjectsByTeamId(int teamId) throws SQLException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT p.* FROM projects p JOIN project_teams pt ON p.project_id = pt.project_id WHERE pt.team_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    projects.add(new Project(
                            rs.getInt("project_id"),
                            rs.getString("project_name"),
                            rs.getString("description"),
                            rs.getDate("start_date").toLocalDate(),
                            rs.getDate("due_date").toLocalDate(),
                            rs.getString("status"),
                            rs.getInt("manager_id")
                    ));
                }
            }
        }
        return projects;
    }
}


