package com.pds.skillify.ui;

import com.pds.controller.Controller;
import com.pds.skillify.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ForgotPasswordWindow extends JFrame {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;

    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton confirmButton;
    private JLabel statusLabel;

    public ForgotPasswordWindow() {
        initialize();
        setVisible(true);
    }

    private void initialize() {
        setTitle("Skillify");
        setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        

        // Campo de Email
        panel.add(new JLabel("Email"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);

        // Campo de Usuario
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Usuario"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        panel.add(usernameField, gbc);

        // Campo de Nueva Contraseña
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Nueva Contraseña"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        // Botón Confirmar
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        confirmButton = new JButton("Confirmar");
        confirmButton.setBackground(new Color(0x80D855));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);
        confirmButton.addActionListener(this::handlePasswordReset);
        panel.add(confirmButton, gbc);

        // Etiqueta de estado
        gbc.gridy++;
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        panel.add(statusLabel, gbc);

        add(panel);
    }

    private void handlePasswordReset(ActionEvent e) {
        String email = emailField.getText().trim();
        String username = usernameField.getText().trim();
        String newPassword = new String(passwordField.getPassword()).trim();

        // Validación de campos vacíos
        if (email.isEmpty() || username.isEmpty() || newPassword.isEmpty()) {
            statusLabel.setText("Todos los campos son obligatorios.");
            return;
        }

        // Buscar usuario
        User user = Controller.getInstance().findUserByEmailAndUsername(email, username);
        if (user == null) {
            statusLabel.setText("Usuario o email incorrecto.");
            return;
        }

        // Cambiar contraseña y confirmar
        Controller.getInstance().updateUserPassword(user, newPassword);
        JOptionPane.showMessageDialog(this, "Contraseña cambiada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        dispose(); // Cierra la ventana después de cambiar la contraseña
    }
}
