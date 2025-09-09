package com.project_management_system;

import com.project_management_system.view.LoginView;

import javax.swing.SwingUtilities;

public class MainApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }
}


