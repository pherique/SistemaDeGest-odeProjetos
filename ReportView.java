package com.project_management_system.view;

import com.project_management_system.controller.ProjectController;
import com.project_management_system.controller.TaskController;
import com.project_management_system.controller.UserController;
import com.project_management_system.model.Project;
import com.project_management_system.model.Task;
import com.project_management_system.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportView extends JFrame {
    private ProjectController projectController;
    private TaskController taskController;
    private UserController userController;

    private JTabbedPane tabbedPane;

    public ReportView() {
        projectController = new ProjectController();
        taskController = new TaskController();
        userController = new UserController();

        setTitle("Relatórios e Dashboards");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        createProjectSummaryReport();
        createCollaboratorPerformanceReport();
        createProjectsAtRiskReport();
    }

    private void createProjectSummaryReport() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        try {
            List<Project> projects = projectController.getAllProjects();
            StringBuilder report = new StringBuilder();
            report.append("### Resumo de Andamento dos Projetos ###\n\n");

            for (Project project : projects) {
                report.append("Projeto: ").append(project.getProjectName()).append("\n");
                report.append("  Descrição: ").append(project.getDescription()).append("\n");
                report.append("  Status: ").append(project.getStatus()).append("\n");
                report.append("  Data de Início: ").append(project.getStartDate()).append("\n");
                report.append("  Data de Término Prevista: ").append(project.getDueDate()).append("\n");

                List<Task> tasks = taskController.getTasksByProjectId(project.getProjectId());
                long totalTasks = tasks.size();
                long completedTasks = tasks.stream().filter(t -> t.getStatus().equals("concluida")).count();

                report.append("  Tarefas: ").append(completedTasks).append(" de ").append(totalTasks).append(" concluídas\n");
                report.append("\n");
            }
            textArea.setText(report.toString());
        } catch (SQLException e) {
            textArea.setText("Erro ao gerar relatório de resumo de projetos: " + e.getMessage());
            e.printStackTrace();
        }

        tabbedPane.addTab("Resumo de Projetos", panel);
    }

    private void createCollaboratorPerformanceReport() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        try {
            List<User> users = userController.getAllUsers();
            StringBuilder report = new StringBuilder();
            report.append("### Desempenho de Cada Colaborador ###\n\n");

            for (User user : users) {
                report.append("Colaborador: ").append(user.getFullName()).append(" (").append(user.getRole()).append(")\n");
                List<Task> assignedTasks = taskController.getTasksByAssignedUserId(user.getUserId());
                long totalAssigned = assignedTasks.size();
                long completedAssigned = assignedTasks.stream().filter(t -> t.getStatus().equals("concluida")).count();

                report.append("  Tarefas Atribuídas: ").append(totalAssigned).append("\n");
                report.append("  Tarefas Concluídas: ").append(completedAssigned).append("\n");
                report.append("\n");
            }
            textArea.setText(report.toString());
        } catch (SQLException e) {
            textArea.setText("Erro ao gerar relatório de desempenho de colaboradores: " + e.getMessage());
            e.printStackTrace();
        }

        tabbedPane.addTab("Desempenho Colaborador", panel);
    }

    private void createProjectsAtRiskReport() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        try {
            List<Project> projectsAtRisk = projectController.getProjectsAtRisk();
            StringBuilder report = new StringBuilder();
            report.append("### Projetos em Risco de Atraso ###\n\n");

            if (projectsAtRisk.isEmpty()) {
                report.append("Nenhum projeto em risco de atraso no momento.\n");
            } else {
                for (Project project : projectsAtRisk) {
                    report.append("Projeto: ").append(project.getProjectName()).append("\n");
                    report.append("  Status: ").append(project.getStatus()).append("\n");
                    report.append("  Data de Término Prevista: ").append(project.getDueDate()).append(" (ATRASADO)\n");
                    report.append("\n");
                }
            }
            textArea.setText(report.toString());
        } catch (SQLException e) {
            textArea.setText("Erro ao gerar relatório de projetos em risco: " + e.getMessage());
            e.printStackTrace();
        }

        tabbedPane.addTab("Projetos em Risco", panel);
    }
}


