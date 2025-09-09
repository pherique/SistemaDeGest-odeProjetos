package com.project_management_system.dao;

import com.project_management_system.model.Project;
import com.project_management_system.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    public void addProject(Project project) throws SQLException {
        String sql = "INSERT INTO projects (project_name, description, start_date, due_date, status, manager_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, project.getProjectName());
            stmt.setString(2, project.getDescription());
            stmt.setDate(3, Date.valueOf(project.getStartDate()));
            stmt.setDate(4, Date.valueOf(project.getDueDate()));
            stmt.setString(5, project.getStatus());
            stmt.setInt(6, project.getManagerId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    project.setProjectId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Project getProjectById(int projectId) throws SQLException {
        String sql = "SELECT * FROM projects WHERE project_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Project(
                            rs.getInt("project_id"),
                            rs.getString("project_name"),
                            rs.getString("description"),
                            rs.getDate("start_date").toLocalDate(),
                            rs.getDate("due_date").toLocalDate(),
                            rs.getString("status"),
                            rs.getInt("manager_id")
                    );
                }
            }
        }
        return null;
    }

    public List<Project> getAllProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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
        return projects;
    }

    public void updateProject(Project project) throws SQLException {
        String sql = "UPDATE projects SET project_name = ?, description = ?, start_date = ?, due_date = ?, status = ?, manager_id = ? WHERE project_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, project.getProjectName());
            stmt.setString(2, project.getDescription());
            stmt.setDate(3, Date.valueOf(project.getStartDate()));
            stmt.setDate(4, Date.valueOf(project.getDueDate()));
            stmt.setString(5, project.getStatus());
            stmt.setInt(6, project.getManagerId());
            stmt.setInt(7, project.getProjectId());
            stmt.executeUpdate();
        }
    }

    public void deleteProject(int projectId) throws SQLException {
        String sql = "DELETE FROM projects WHERE project_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            stmt.executeUpdate();
        }
    }
}


