package com.project_management_system.controller;

import com.project_management_system.dao.TaskDAO;
import com.project_management_system.model.Task;

import java.sql.SQLException;
import java.util.List;

public class TaskController {
    private TaskDAO taskDAO;

    public TaskController() {
        this.taskDAO = new TaskDAO();
    }

    public void createTask(Task task) throws SQLException {
        taskDAO.addTask(task);
    }

    public Task getTaskById(int taskId) throws SQLException {
        return taskDAO.getTaskById(taskId);
    }

    public List<Task> getAllTasks() throws SQLException {
        return taskDAO.getAllTasks();
    }

    public List<Task> getTasksByProjectId(int projectId) throws SQLException {
        return taskDAO.getTasksByProjectId(projectId);
    }

    public List<Task> getTasksByAssignedUserId(int userId) throws SQLException {
        return taskDAO.getTasksByAssignedUserId(userId);
    }

    public void updateTask(Task task) throws SQLException {
        taskDAO.updateTask(task);
    }

    public void deleteTask(int taskId) throws SQLException {
        taskDAO.deleteTask(taskId);
    }
}


