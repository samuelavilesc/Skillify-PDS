package com.pds.skillify.ui.controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.pds.skillify.controller.Controller;
import com.pds.skillify.ui.ForgotPasswordWindow;
import com.pds.skillify.ui.LoginWindow;
import com.pds.skillify.ui.MainWindow;
import com.pds.skillify.ui.RegisterWindow;

public class LoginWindowController {

    private LoginWindow view;

    /**
     * Color del borde utilizado para mostrar errores en los campos de entrada.
     */
    private static final Color ERROR_BORDER_COLOR = Color.RED;

    /**
     * Color del borde predeterminado para los campos de entrada.
     */
    private static final Color DEFAULT_BORDER_COLOR = Color.GRAY;

    public LoginWindowController(LoginWindow view) {
        this.view = view;
        initializeControllers();
    }

    private void initializeControllers() {
        Controller.getInstance(); // Iniciar adaptadores
        handleClickOnRegister(view.getRegisterLabel());
        handleClickOnForgotPassword(view.getForgotPasswordLabel());
        handleClickOnLogin(view.getLoginButton());
        handleEnterKeyLogin(view.getPasswordField());
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); // Apagar el programa al cerrar la ventana
            }
        });
    }

    private void handleClickOnRegister(JLabel registerLabel) {
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.dispose();
                new RegisterWindow();
            }
        });
    }

    private void handleClickOnForgotPassword(JLabel forgotPasswordLabel) {
        forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ForgotPasswordWindow(); // Abre la ventana de recuperación de contraseña
            }
        });
    }

    private void handleClickOnLogin(JButton loginButton) {
        loginButton.addActionListener(e -> login());
    }

    @SuppressWarnings("deprecation")
    private void login() {
        String username = view.getUsernameField().getText();
        String password = view.getPasswordField().getText();
        if (validateFields(username, password)) {
            if (Controller.getInstance().login(username, password)) {
                view.dispose();
                new MainWindow();
            } else {
                showErrorMessage("Datos incorrectos. Intente de nuevo.");
            }
        }
    }

    private void handleEnterKeyLogin(JPasswordField pwdField) {
        pwdField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    login();
                }
            }
        });
    }

    private boolean validateFields(String username, String password) {
        boolean isValid = true;

        // Validar usuario
        if (username == null || username.isEmpty()) {
            view.getUsernameField().setBorder(BorderFactory.createLineBorder(ERROR_BORDER_COLOR));
            isValid = false;
        } 

        // Validar contraseña
        if (password == null || password.isEmpty()) {
            view.getPasswordField().setBorder(BorderFactory.createLineBorder(ERROR_BORDER_COLOR));
            isValid = false;
        }

        return isValid;
    }

    /**
     * Muestra un mensaje de error
     */
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(view, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
