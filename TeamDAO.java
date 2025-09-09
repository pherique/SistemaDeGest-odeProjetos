package com.project_management_system.dao;

import com.project_management_system.model.Team;
import com.project_management_system.model.User;
import com.project_management_system.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO {

    public void addTeam(Team team) throws SQLException {
        String sql = "INSERT INTO teams (team_name, description) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, team.getTeamName());
            stmt.setString(2, team.getDescription());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    team.setTeamId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Team getTeamById(int teamId) throws SQLException {
        String sql = "SELECT * FROM teams WHERE team_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Team(
                            rs.getInt("team_id"),
                            rs.getString("team_name"),
                            rs.getString("description")
                    );
                }
            }
        }
        return null;
    }

    public List<Team> getAllTeams() throws SQLException {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT * FROM teams";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                teams.add(new Team(
                        rs.getInt("team_id"),
                        rs.getString("team_name"),
                        rs.getString("description")
                ));
            }
        }
        return teams;
    }

    public void updateTeam(Team team) throws SQLException {
        String sql = "UPDATE teams SET team_name = ?, description = ? WHERE team_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, team.getTeamName());
            stmt.setString(2, team.getDescription());
            stmt.setInt(3, team.getTeamId());
            stmt.executeUpdate();
        }
    }

    public void deleteTeam(int teamId) throws SQLException {
        String sql = "DELETE FROM teams WHERE team_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            stmt.executeUpdate();
        }
    }

    public void addTeamMember(int teamId, int userId) throws SQLException {
        String sql = "INSERT INTO team_members (team_id, user_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    public void removeTeamMember(int teamId, int userId) throws SQLException {
        String sql = "DELETE FROM team_members WHERE team_id = ? AND user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    public List<User> getTeamMembers(int teamId) throws SQLException {
        List<User> members = new ArrayList<>();
        String sql = "SELECT u.* FROM users u JOIN team_members tm ON u.user_id = tm.user_id WHERE tm.team_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    members.add(new User(
                            rs.getInt("user_id"),
                            rs.getString("full_name"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getString("role"),
                            rs.getString("username"),
                            rs.getString("password")
                    ));
                }
            }
        }
        return members;
    }
}


