package com.pds.skillify.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.pds.skillify.ui.controller.RegisterWindowController;

@SuppressWarnings("serial")
public class RegisterWindow extends JFrame {

	private JLabel loginLabel;
	
	private JTextField usernameField;
	private JTextField emailField;
	private JTextField passwordField;
	
	private JButton createAccountButton;
	private JButton selectAvatarButton;
	
	private static final int WIDTH = 360;
	private static final int HEIGHT = 550;
	
	public RegisterWindow() {
		initialize();
		new RegisterWindowController(this);
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
		panelCentro.setBorder(BorderFactory.createEmptyBorder(1, 50, 1, 50));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.weightx = 1.0;

		JLabel usernameLabel = new JLabel("Usuario");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		panelCentro.add(usernameLabel, gbc);

		usernameField = new JTextField();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		panelCentro.add(usernameField, gbc);

		JLabel emailLabel = new JLabel("Email");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		panelCentro.add(emailLabel, gbc);

		emailField = new JTextField();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		panelCentro.add(emailField, gbc);

		JLabel passwordLabel = new JLabel("Contraseña");
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		panelCentro.add(passwordLabel, gbc);

		passwordField = new JPasswordField();
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		panelCentro.add(passwordField, gbc);

		add(panelCentro, BorderLayout.CENTER);

		JLabel genericAvatarLabel = new JLabel(new ImageIcon(getClass().getResource("/user.png")));
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		panelCentro.add(genericAvatarLabel, gbc);

		selectAvatarButton = new JButton("Seleccionar");
		gbc.gridx = 1;
		gbc.gridy = 6;
		panelCentro.add(selectAvatarButton, gbc);

		
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbcBottom = new GridBagConstraints();
		gbcBottom.gridx = 0;
		gbcBottom.insets = new Insets(2, 0, 2, 0);
		gbcBottom.anchor = GridBagConstraints.CENTER;

		gbcBottom.gridy = 0;
		createAccountButton = new JButton("Crear Cuenta");
		createAccountButton.setPreferredSize(new Dimension(120, 30));
		bottomPanel.add(createAccountButton, gbcBottom);

		gbcBottom.gridy = 1;
		JLabel alreadyHasAccountLabel = new JLabel("¿Ya tienes cuenta?");
		loginLabel = new JLabel("Inicia sesión");
		loginLabel.setForeground(new Color(0x80D855));
		loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

		JPanel registerPanel = new JPanel();
		registerPanel.add(alreadyHasAccountLabel);
		registerPanel.add(loginLabel);
		bottomPanel.add(registerPanel, gbcBottom);


		add(bottomPanel, BorderLayout.SOUTH);
	}

	public JLabel getLoginLabel() {
		return loginLabel;
	}

	public JTextField getUsernameField() {
		return usernameField;
	}

	public JTextField getEmailField() {
		return emailField;
	}

	public JTextField getPasswordField() {
		return passwordField;
	}

	public JButton getCreateAccountButton() {
		return createAccountButton;
	}

	public JButton getSelectAvatarButton() {
		return selectAvatarButton;
	}

}