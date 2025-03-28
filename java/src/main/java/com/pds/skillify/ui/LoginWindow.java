package com.pds.skillify.ui;

import javax.swing.*;

import com.pds.skillify.ui.controller.LoginWindowController;

import java.awt.*;

@SuppressWarnings("serial")
public class LoginWindow extends JFrame {

	private static final int WIDTH = 360;
	private static final int HEIGHT = 430;

	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JLabel registerLabel;
	private JLabel forgotPasswordLabel;

	public LoginWindow() {
		initialize();
		new LoginWindowController(this);
		setVisible(true);
	}

	private void initialize() {
		setTitle("Skillify");
		setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());

		ImageIcon logo = new ImageIcon(getClass().getResource("/logo.png"));
		Image img = logo.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
		ImageIcon resizedLogo = new ImageIcon(img);

		JLabel logoLabel = new JLabel(resizedLogo);
		logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

		topPanel.add(logoLabel);
		add(topPanel, BorderLayout.NORTH);

		JPanel panelCentro = new JPanel(new GridBagLayout());
		panelCentro.setBorder(BorderFactory.createEmptyBorder(1, 20, 1, 20));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel usernameLabel = new JLabel("Usuario");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		panelCentro.add(usernameLabel, gbc);

		usernameField = new JTextField();
		usernameField.setPreferredSize(new Dimension(200, 30));
		gbc.gridx = 0;
		gbc.gridy = 1;
		panelCentro.add(usernameField, gbc);

		JLabel passwordLabel = new JLabel("Contraseña");
		gbc.gridx = 0;
		gbc.gridy = 2;
		panelCentro.add(passwordLabel, gbc);

		passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(200, 30));
		gbc.gridx = 0;
		gbc.gridy = 3;
		panelCentro.add(passwordField, gbc);

		add(panelCentro, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbcBottom = new GridBagConstraints();
		gbcBottom.gridx = 0;
		gbcBottom.insets = new Insets(2, 0, 2, 0);
		gbcBottom.anchor = GridBagConstraints.CENTER;

		gbcBottom.gridy = 0;
		loginButton = new JButton("Login");
		loginButton.setPreferredSize(new Dimension(120, 30));
		bottomPanel.add(loginButton, gbcBottom);

		gbcBottom.gridy = 1;
		JLabel registerTextLabel = new JLabel("¿No tienes cuenta? ");
		registerLabel = new JLabel("Regístrate");
		registerLabel.setForeground(new Color(0x80D855));
		registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

		JPanel registerPanel = new JPanel();
		registerPanel.add(registerTextLabel);
		registerPanel.add(registerLabel);
		bottomPanel.add(registerPanel, gbcBottom);

		gbcBottom.gridy = 2;
		gbcBottom.insets = new Insets(2, 0, 20, 0);
		forgotPasswordLabel = new JLabel("He olvidado mi contraseña", SwingConstants.CENTER);
		forgotPasswordLabel.setForeground(new Color(0x80D855));
		forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

		bottomPanel.add(forgotPasswordLabel, gbcBottom);

		add(bottomPanel, BorderLayout.SOUTH);
	}

	public JTextField getUsernameField() {
		return usernameField;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public JButton getLoginButton() {
		return loginButton;
	}

	public JLabel getRegisterLabel() {
		return registerLabel;
	}

	public JLabel getForgotPasswordLabel() {
		return forgotPasswordLabel;
	}
}
