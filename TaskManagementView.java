package com.project_management_system.view;

import com.project_management_system.controller.TaskController;
import com.project_management_system.controller.ProjectController;
import com.project_management_system.controller.UserController;
import com.project_management_system.model.Task;
import com.project_management_system.model.Project;
import com.project_management_system.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TaskManagementView extends JFrame {
    private User loggedInUser;
    private TaskController taskController;
    private ProjectController projectController;
    private UserController userController;
    private JTable taskTable;
    private DefaultTableModel tableModel;

    private JTextField titleField;
    private JTextArea descriptionArea;
    private JComboBox<Project> projectComboBox;
    private JComboBox<User> assignedToComboBox;
    private JComboBox<String> statusComboBox;
    private JTextField plannedStartDateField;
    private JTextField plannedEndDateField;
    private JTextField actualStartDateField;
    private JTextField actualEndDateField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public TaskManagementView(User user) {
        this.loggedInUser = user;
        taskController = new TaskController();
        projectController = new ProjectController();
        userController = new UserController();

        setTitle("Gestão de Tarefas");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Task Form Panel
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Detalhes da Tarefa"));

        titleField = new JTextField();
        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        projectComboBox = new JComboBox<>();
        assignedToComboBox = new JComboBox<>();
        statusComboBox = new JComboBox<>(new String[]{"pendente", "em_execucao", "concluida"});
        plannedStartDateField = new JTextField();
        plannedEndDateField = new JTextField();
        actualStartDateField = new JTextField();
        actualEndDateField = new JTextField();

        loadProjects();
        loadUsers();

        formPanel.add(new JLabel("Título:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Descrição:"));
        formPanel.add(descriptionScrollPane);
        formPanel.add(new JLabel("Projeto Vinculado:"));
        formPanel.add(projectComboBox);
        formPanel.add(new JLabel("Responsável:"));
        formPanel.add(assignedToComboBox);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(statusComboBox);
        formPanel.add(new JLabel("Data Início Prevista (AAAA-MM-DD):"));
        formPanel.add(plannedStartDateField);
        formPanel.add(new JLabel("Data Fim Prevista (AAAA-MM-DD):"));
        formPanel.add(plannedEndDateField);
        formPanel.add(new JLabel("Data Início Real (AAAA-MM-DD):"));
        formPanel.add(actualStartDateField);
        formPanel.add(new JLabel("Data Fim Real (AAAA-MM-DD):"));
        formPanel.add(actualEndDateField);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addButton = new JButton("Adicionar");
        updateButton = new JButton("Atualizar");
        deleteButton = new JButton("Excluir");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Table Panel
        String[] columnNames = {"ID", "Título", "Projeto", "Responsável", "Status", "Início Previsto", "Fim Previsto", "Início Real", "Fim Real"};
        tableModel = new DefaultTableModel(columnNames, 0);
        taskTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(taskTable);

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadTasks();
        addListeners();
        applyAccessControl();
    }

    private void loadProjects() {
        try {
            List<Project> projects = projectController.getAllProjects();
            for (Project project : projects) {
                projectComboBox.addItem(project);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar projetos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        try {
            List<User> users = userController.getAllUsers();
            for (User user : users) {
                assignedToComboBox.addItem(user);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuários: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadTasks() {
        tableModel.setRowCount(0); // Clear existing data
        try {
            List<Task> tasks;
            if (loggedInUser.getRole().equals("colaborador")) {
                tasks = taskController.getTasksByAssignedUserId(loggedInUser.getUserId());
            } else {
                tasks = taskController.getAllTasks();
            }

            for (Task task : tasks) {
                Project project = projectController.getProjectById(task.getProjectId());
                User assignedTo = userController.getUserById(task.getAssignedToUserId());
                String projectName = (project != null) ? project.getProjectName() : "Desconhecido";
                String assignedToName = (assignedTo != null) ? assignedTo.getFullName() : "Desconhecido";

                tableModel.addRow(new Object[]{
                        task.getTaskId(),
                        task.getTitle(),
                        projectName,
                        assignedToName,
                        task.getStatus(),
                        task.getPlannedStartDate(),
                        task.getPlannedEndDate(),
                        task.getActualStartDate(),
                        task.getActualEndDate()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tarefas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void addListeners() {
        addButton.addActionListener(e -> {
            try {
                LocalDate plannedStartDate = LocalDate.parse(plannedStartDateField.getText());
                LocalDate plannedEndDate = LocalDate.parse(plannedEndDateField.getText());
                LocalDate actualStartDate = actualStartDateField.getText().isEmpty() ? null : LocalDate.parse(actualStartDateField.getText());
                LocalDate actualEndDate = actualEndDateField.getText().isEmpty() ? null : LocalDate.parse(actualEndDateField.getText());

                Project selectedProject = (Project) projectComboBox.getSelectedItem();
                User selectedUser = (User) assignedToComboBox.getSelectedItem();

                if (selectedProject == null || selectedUser == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um projeto e um responsável.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Task newTask = new Task(
                        0, // ID will be generated by DB
                        titleField.getText(),
                        descriptionArea.getText(),
                        selectedProject.getProjectId(),
                        selectedUser.getUserId(),
                        (String) statusComboBox.getSelectedItem(),
                        plannedStartDate,
                        plannedEndDate,
                        actualStartDate,
                        actualEndDate
                );
                taskController.createTask(newTask);
                JOptionPane.showMessageDialog(this, "Tarefa adicionada com sucesso!");
                clearForm();
                loadTasks();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido. Use AAAA-MM-DD.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar tarefa: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = taskTable.getSelectedRow();
            if (selectedRow >= 0) {
                try {
                    int taskId = (int) tableModel.getValueAt(selectedRow, 0);
                    LocalDate plannedStartDate = LocalDate.parse(plannedStartDateField.getText());
                    LocalDate plannedEndDate = LocalDate.parse(plannedEndDateField.getText());
                    LocalDate actualStartDate = actualStartDateField.getText().isEmpty() ? null : LocalDate.parse(actualStartDateField.getText());
                    LocalDate actualEndDate = actualEndDateField.getText().isEmpty() ? null : LocalDate.parse(actualEndDateField.getText());

                    Project selectedProject = (Project) projectComboBox.getSelectedItem();
                    User selectedUser = (User) assignedToComboBox.getSelectedItem();

                    if (selectedProject == null || selectedUser == null) {
                        JOptionPane.showMessageDialog(this, "Selecione um projeto e um responsável.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Task updatedTask = new Task(
                            taskId,
                            titleField.getText(),
                            descriptionArea.getText(),
                            selectedProject.getProjectId(),
                            selectedUser.getUserId(),
                            (String) statusComboBox.getSelectedItem(),
                            plannedStartDate,
                            plannedEndDate,
                            actualStartDate,
                            actualEndDate
                    );

                    // Allow collaborators to only update their own tasks and only the status field
                    if (loggedInUser.getRole().equals("colaborador")) {
                        Task originalTask = taskController.getTaskById(taskId);
                        if (originalTask.getAssignedToUserId() == loggedInUser.getUserId()) {
                            // Only update status
                            originalTask.setStatus(updatedTask.getStatus());
                            taskController.updateTask(originalTask);
                            JOptionPane.showMessageDialog(this, "Status da tarefa atualizado com sucesso!");
                        } else {
                            JOptionPane.showMessageDialog(this, "Você só pode atualizar o status de suas próprias tarefas.", "Permissão Negada", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        taskController.updateTask(updatedTask);
                        JOptionPane.showMessageDialog(this, "Tarefa atualizada com sucesso!");
                    }

                    clearForm();
                    loadTasks();
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(this, "Formato de data inválido. Use AAAA-MM-DD.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar tarefa: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma tarefa para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = taskTable.getSelectedRow();
            if (selectedRow >= 0) {
                int taskId = (int) tableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir esta tarefa?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        taskController.deleteTask(taskId);
                        JOptionPane.showMessageDialog(this, "Tarefa excluída com sucesso!");
                        clearForm();
                        loadTasks();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir tarefa: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma tarefa para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        taskTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && taskTable.getSelectedRow() != -1) {
                int selectedRow = taskTable.getSelectedRow();
                titleField.setText((String) tableModel.getValueAt(selectedRow, 1));
                // For Project and User, you'll need to find the corresponding object in the ComboBox
                // This requires iterating through the items and matching by ID or name
                // For simplicity, let's assume the Project and User objects are correctly loaded in the ComboBoxes
                // and we can select them by their string representation for now.
                // A more robust solution would involve storing the actual objects in the table model or mapping IDs.
                
                // Selecting Project
                String projectName = (String) tableModel.getValueAt(selectedRow, 2);
                for (int i = 0; i < projectComboBox.getItemCount(); i++) {
                    if (projectComboBox.getItemAt(i).getProjectName().equals(projectName)) {
                        projectComboBox.setSelectedIndex(i);
                        break;
                    }
                }

                // Selecting User
                String assignedToName = (String) tableModel.getValueAt(selectedRow, 3);
                for (int i = 0; i < assignedToComboBox.getItemCount(); i++) {
                    if (assignedToComboBox.getItemAt(i).getFullName().equals(assignedToName)) {
                        assignedToComboBox.setSelectedIndex(i);
                        break;
                    }
                }

                statusComboBox.setSelectedItem((String) tableModel.getValueAt(selectedRow, 4));
                plannedStartDateField.setText(tableModel.getValueAt(selectedRow, 5) != null ? tableModel.getValueAt(selectedRow, 5).toString() : "");
                plannedEndDateField.setText(tableModel.getValueAt(selectedRow, 6) != null ? tableModel.getValueAt(selectedRow, 6).toString() : "");
                actualStartDateField.setText(tableModel.getValueAt(selectedRow, 7) != null ? tableModel.getValueAt(selectedRow, 7).toString() : "");
                actualEndDateField.setText(tableModel.getValueAt(selectedRow, 8) != null ? tableModel.getValueAt(selectedRow, 8).toString() : "");
            }
        });
    }

    private void clearForm() {
        titleField.setText("");
        descriptionArea.setText("");
        projectComboBox.setSelectedIndex(0);
        assignedToComboBox.setSelectedIndex(0);
        statusComboBox.setSelectedIndex(0);
        plannedStartDateField.setText("");
        plannedEndDateField.setText("");
        actualStartDateField.setText("");
        actualEndDateField.setText("");
        taskTable.clearSelection();
    }

    private void applyAccessControl() {
        if (loggedInUser.getRole().equals("colaborador")) {
            addButton.setEnabled(false);
            deleteButton.setEnabled(false);
            titleField.setEditable(false);
            descriptionArea.setEditable(false);
            projectComboBox.setEnabled(false);
            assignedToComboBox.setEnabled(false);
            plannedStartDateField.setEditable(false);
            plannedEndDateField.setEditable(false);
            actualStartDateField.setEditable(false);
            actualEndDateField.setEditable(false);
        }
    }
}


