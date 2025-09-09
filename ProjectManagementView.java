package com.project_management_system.view;

import com.project_management_system.controller.ProjectController;
import com.project_management_system.controller.UserController;
import com.project_management_system.model.Project;
import com.project_management_system.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ProjectManagementView extends JFrame {
    private User loggedInUser;
    private ProjectController projectController;
    private UserController userController;
    private JTable projectTable;
    private DefaultTableModel tableModel;

    private JTextField projectNameField;
    private JTextArea descriptionArea;
    private JTextField startDateField;
    private JTextField dueDateField;
    private JComboBox<String> statusComboBox;
    private JComboBox<User> managerComboBox;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public ProjectManagementView(User user) {
        this.loggedInUser = user;
        projectController = new ProjectController();
        userController = new UserController();

        setTitle("Gestão de Projetos");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Project Form Panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Detalhes do Projeto"));

        projectNameField = new JTextField();
        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        startDateField = new JTextField();
        dueDateField = new JTextField();
        statusComboBox = new JComboBox<>(new String[]{"planejado", "em_andamento", "concluido", "cancelado"});
        managerComboBox = new JComboBox<>();
        loadManagers();

        formPanel.add(new JLabel("Nome do Projeto:"));
        formPanel.add(projectNameField);
        formPanel.add(new JLabel("Descrição:"));
        formPanel.add(descriptionScrollPane);
        formPanel.add(new JLabel("Data de Início (AAAA-MM-DD):"));
        formPanel.add(startDateField);
        formPanel.add(new JLabel("Data de Término Prevista (AAAA-MM-DD):"));
        formPanel.add(dueDateField);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(statusComboBox);
        formPanel.add(new JLabel("Gerente Responsável:"));
        formPanel.add(managerComboBox);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addButton = new JButton("Adicionar");
        updateButton = new JButton("Atualizar");
        deleteButton = new JButton("Excluir");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Table Panel
        String[] columnNames = {"ID", "Nome", "Descrição", "Início", "Término Previsto", "Status", "Gerente ID", "Gerente"};
        tableModel = new DefaultTableModel(columnNames, 0);
        projectTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(projectTable);

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadProjects();
        addListeners();
        applyAccessControl();
    }

    private void loadManagers() {
        try {
            List<User> managers = userController.getAllUsers(); // Assuming all users can be managers for now
            for (User user : managers) {
                managerComboBox.addItem(user);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar gerentes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadProjects() {
        tableModel.setRowCount(0); // Clear existing data
        try {
            List<Project> projects = projectController.getAllProjects();
            for (Project project : projects) {
                User manager = userController.getUserById(project.getManagerId());
                String managerName = (manager != null) ? manager.getFullName() : "Desconhecido";
                tableModel.addRow(new Object[]{
                        project.getProjectId(),
                        project.getProjectName(),
                        project.getDescription(),
                        project.getStartDate(),
                        project.getDueDate(),
                        project.getStatus(),
                        project.getManagerId(),
                        managerName
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar projetos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void addListeners() {
        addButton.addActionListener(e -> {
            try {
                LocalDate startDate = LocalDate.parse(startDateField.getText());
                LocalDate dueDate = LocalDate.parse(dueDateField.getText());
                User selectedManager = (User) managerComboBox.getSelectedItem();

                if (selectedManager == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um gerente responsável.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Project newProject = new Project(
                        0, // ID will be generated by DB
                        projectNameField.getText(),
                        descriptionArea.getText(),
                        startDate,
                        dueDate,
                        (String) statusComboBox.getSelectedItem(),
                        selectedManager.getUserId()
                );
                projectController.createProject(newProject);
                JOptionPane.showMessageDialog(this, "Projeto adicionado com sucesso!");
                clearForm();
                loadProjects();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido. Use AAAA-MM-DD.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar projeto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = projectTable.getSelectedRow();
            if (selectedRow >= 0) {
                try {
                    int projectId = (int) tableModel.getValueAt(selectedRow, 0);
                    LocalDate startDate = LocalDate.parse(startDateField.getText());
                    LocalDate dueDate = LocalDate.parse(dueDateField.getText());
                    User selectedManager = (User) managerComboBox.getSelectedItem();

                    if (selectedManager == null) {
                        JOptionPane.showMessageDialog(this, "Selecione um gerente responsável.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Project updatedProject = new Project(
                            projectId,
                            projectNameField.getText(),
                            descriptionArea.getText(),
                            startDate,
                            dueDate,
                            (String) statusComboBox.getSelectedItem(),
                            selectedManager.getUserId()
                    );
                    projectController.updateProject(updatedProject);
                    JOptionPane.showMessageDialog(this, "Projeto atualizado com sucesso!");
                    clearForm();
                    loadProjects();
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(this, "Formato de data inválido. Use AAAA-MM-DD.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar projeto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um projeto para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = projectTable.getSelectedRow();
            if (selectedRow >= 0) {
                int projectId = (int) tableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este projeto?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        projectController.deleteProject(projectId);
                        JOptionPane.showMessageDialog(this, "Projeto excluído com sucesso!");
                        clearForm();
                        loadProjects();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir projeto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um projeto para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        projectTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && projectTable.getSelectedRow() != -1) {
                int selectedRow = projectTable.getSelectedRow();
                projectNameField.setText((String) tableModel.getValueAt(selectedRow, 1));
                descriptionArea.setText((String) tableModel.getValueAt(selectedRow, 2));
                startDateField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                dueDateField.setText(tableModel.getValueAt(selectedRow, 4).toString());
                statusComboBox.setSelectedItem((String) tableModel.getValueAt(selectedRow, 5));
                
                // Select manager in combo box
                int managerId = (int) tableModel.getValueAt(selectedRow, 6);
                for (int i = 0; i < managerComboBox.getItemCount(); i++) {
                    User manager = managerComboBox.getItemAt(i);
                    if (manager.getUserId() == managerId) {
                        managerComboBox.setSelectedItem(manager);
                        break;
                    }
                }
            }
        });
    }

    private void clearForm() {
        projectNameField.setText("");
        descriptionArea.setText("");
        startDateField.setText("");
        dueDateField.setText("");
        statusComboBox.setSelectedIndex(0);
        managerComboBox.setSelectedIndex(0);
        projectTable.clearSelection();
    }

    private void applyAccessControl() {
        if (loggedInUser.getRole().equals("colaborador")) {
            addButton.setEnabled(false);
            updateButton.setEnabled(false);
            deleteButton.setEnabled(false);
            projectNameField.setEditable(false);
            descriptionArea.setEditable(false);
            startDateField.setEditable(false);
            dueDateField.setEditable(false);
            statusComboBox.setEnabled(false);
            managerComboBox.setEnabled(false);
        }
    }
}


