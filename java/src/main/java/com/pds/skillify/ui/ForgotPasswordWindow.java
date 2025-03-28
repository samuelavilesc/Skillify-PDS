package com.pds.skillify.ui;

import com.pds.skillify.ui.controller.ForgotPasswordWindowController;

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

	private ForgotPasswordWindowController controller;

	public ForgotPasswordWindow() {
		initialize();
		controller = new ForgotPasswordWindowController(this);
		setVisible(true);
	}

	private void initialize() {
		setTitle("Skillify");
		setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;

		panel.add(new JLabel("Email"), gbc);
		gbc.gridx = 1;
		emailField = new JTextField(20);
		panel.add(emailField, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		panel.add(new JLabel("Usuario"), gbc);
		gbc.gridx = 1;
		usernameField = new JTextField(20);
		panel.add(usernameField, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		panel.add(new JLabel("Nueva Contraseña"), gbc);
		gbc.gridx = 1;
		passwordField = new JPasswordField(20);
		panel.add(passwordField, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 2;
		confirmButton = new JButton("Confirmar");
		confirmButton.setBackground(new Color(0x80D855));
		confirmButton.setForeground(Color.WHITE);
		confirmButton.setFocusPainted(false);
		confirmButton.addActionListener(this::handlePasswordReset);
		panel.add(confirmButton, gbc);

		gbc.gridy++;
		statusLabel = new JLabel("", SwingConstants.CENTER);
		statusLabel.setForeground(Color.RED);
		panel.add(statusLabel, gbc);

		add(panel);
	}

	private void handlePasswordReset(ActionEvent e) {
		controller.handlePasswordReset();
	}

	public String getEmail() {
		return emailField.getText().trim();
	}

	public String getUsername() {
		return usernameField.getText().trim();
	}

	public String getNewPassword() {
		return new String(passwordField.getPassword()).trim();
	}

	public void showStatusMessage(String message, boolean success) {
		statusLabel.setText(message);
		statusLabel.setForeground(success ? new Color(0x4CAF50) : Color.RED);
	}

	public void closeWindow() {
		dispose();
	}
}
