package com.project_management_system.dao;

import com.project_management_system.model.Task;
import com.project_management_system.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public void addTask(Task task) throws SQLException {
        String sql = "INSERT INTO tasks (title, description, project_id, assigned_to_user_id, status, planned_start_date, planned_end_date, actual_start_date, actual_end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setInt(3, task.getProjectId());
            stmt.setInt(4, task.getAssignedToUserId());
            stmt.setString(5, task.getStatus());
            stmt.setDate(6, Date.valueOf(task.getPlannedStartDate()));
            stmt.setDate(7, Date.valueOf(task.getPlannedEndDate()));
            stmt.setDate(8, task.getActualStartDate() != null ? Date.valueOf(task.getActualStartDate()) : null);
            stmt.setDate(9, task.getActualEndDate() != null ? Date.valueOf(task.getActualEndDate()) : null);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    task.setTaskId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Task getTaskById(int taskId) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE task_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, taskId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Task(
                            rs.getInt("task_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getInt("project_id"),
                            rs.getInt("assigned_to_user_id"),
                            rs.getString("status"),
                            rs.getDate("planned_start_date").toLocalDate(),
                            rs.getDate("planned_end_date").toLocalDate(),
                            rs.getDate("actual_start_date") != null ? rs.getDate("actual_start_date").toLocalDate() : null,
                            rs.getDate("actual_end_date") != null ? rs.getDate("actual_end_date").toLocalDate() : null
                    );
                }
            }
        }
        return null;
    }

    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("task_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("project_id"),
                        rs.getInt("assigned_to_user_id"),
                        rs.getString("status"),
                        rs.getDate("planned_start_date").toLocalDate(),
                        rs.getDate("planned_end_date").toLocalDate(),
                        rs.getDate("actual_start_date") != null ? rs.getDate("actual_start_date").toLocalDate() : null,
                        rs.getDate("actual_end_date") != null ? rs.getDate("actual_end_date").toLocalDate() : null
                ));
            }
        }
        return tasks;
    }

    public List<Task> getTasksByProjectId(int projectId) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE project_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add(new Task(
                            rs.getInt("task_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getInt("project_id"),
                            rs.getInt("assigned_to_user_id"),
                            rs.getString("status"),
                            rs.getDate("planned_start_date").toLocalDate(),
                            rs.getDate("planned_end_date").toLocalDate(),
                            rs.getDate("actual_start_date") != null ? rs.getDate("actual_start_date").toLocalDate() : null,
                            rs.getDate("actual_end_date") != null ? rs.getDate("actual_end_date").toLocalDate() : null
                    ));
                }
            }
        }
        return tasks;
    }

    public List<Task> getTasksByAssignedUserId(int userId) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE assigned_to_user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add(new Task(
                            rs.getInt("task_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getInt("project_id"),
                            rs.getInt("assigned_to_user_id"),
                            rs.getString("status"),
                            rs.getDate("planned_start_date").toLocalDate(),
                            rs.getDate("planned_end_date").toLocalDate(),
                            rs.getDate("actual_start_date") != null ? rs.getDate("actual_start_date").toLocalDate() : null,
                            rs.getDate("actual_end_date") != null ? rs.getDate("actual_end_date").toLocalDate() : null
                    ));
                }
            }
        }
        return tasks;
    }

    public void updateTask(Task task) throws SQLException {
        String sql = "UPDATE tasks SET title = ?, description = ?, project_id = ?, assigned_to_user_id = ?, status = ?, planned_start_date = ?, planned_end_date = ?, actual_start_date = ?, actual_end_date = ? WHERE task_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setInt(3, task.getProjectId());
            stmt.setInt(4, task.getAssignedToUserId());
            stmt.setString(5, task.getStatus());
            stmt.setDate(6, Date.valueOf(task.getPlannedStartDate()));
            stmt.setDate(7, Date.valueOf(task.getPlannedEndDate()));
            stmt.setDate(8, task.getActualStartDate() != null ? Date.valueOf(task.getActualStartDate()) : null);
            stmt.setDate(9, task.getActualEndDate() != null ? Date.valueOf(task.getActualEndDate()) : null);
            stmt.setInt(10, task.getTaskId());
            stmt.executeUpdate();
        }
    }

    public void deleteTask(int taskId) throws SQLException {
        String sql = "DELETE FROM tasks WHERE task_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, taskId);
            stmt.executeUpdate();
        }
    }
}


