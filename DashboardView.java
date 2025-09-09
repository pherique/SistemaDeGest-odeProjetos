package com.project_management_system.view;

import com.project_management_system.model.User;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JFrame {
    private User loggedInUser;

    public DashboardView(User user) {
        this.loggedInUser = user;

        setTitle("Dashboard - Sistema de Gestão de Projetos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Arquivo");
        JMenuItem logoutItem = new JMenuItem("Sair");
        logoutItem.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });
        fileMenu.add(logoutItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        JLabel welcomeLabel = new JLabel("Bem-vindo, " + loggedInUser.getFullName() + " (" + loggedInUser.getRole() + ")!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add buttons based on user role
        if (loggedInUser.getRole().equals("administrador")) {
            JButton userManagementButton = new JButton("Gerenciar Usuários");
            userManagementButton.addActionListener(e -> {
                UserManagementView userManagementView = new UserManagementView(loggedInUser);
                userManagementView.setVisible(true);
            });
            contentPanel.add(userManagementButton);
        }

        if (loggedInUser.getRole().equals("administrador") || loggedInUser.getRole().equals("gerente")) {
            JButton projectManagementButton = new JButton("Gerenciar Projetos");
            projectManagementButton.addActionListener(e -> {
                ProjectManagementView projectManagementView = new ProjectManagementView(loggedInUser);
                projectManagementView.setVisible(true);
            });
            contentPanel.add(projectManagementButton);

            JButton teamManagementButton = new JButton("Gerenciar Equipes");
            teamManagementButton.addActionListener(e -> {
                TeamManagementView teamManagementView = new TeamManagementView(loggedInUser);
                teamManagementView.setVisible(true);
            });
            contentPanel.add(teamManagementButton);
        }

        JButton taskManagementButton = new JButton("Gerenciar Tarefas");
        taskManagementButton.addActionListener(e -> {
            TaskManagementView taskManagementView = new TaskManagementView(loggedInUser);
            taskManagementView.setVisible(true);
        });
        contentPanel.add(taskManagementButton);

        JButton reportsButton = new JButton("Relatórios e Dashboards");
        reportsButton.addActionListener(e -> {
            ReportView reportView = new ReportView();
            reportView.setVisible(true);
        });
        contentPanel.add(reportsButton);
    }
}


