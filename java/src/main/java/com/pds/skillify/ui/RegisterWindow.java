package com.pds.skillify.ui;

import java.awt.*;
import javax.swing.*;

import com.pds.skillify.ui.controller.RegisterWindowController;

@SuppressWarnings("serial")
public class RegisterWindow extends JFrame {

	private JLabel loginLabel;
	private JTextField usernameField;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JButton createAccountButton;
	private JButton selectAvatarButton;
	private JLabel genericAvatarLabel;
	private ImageIcon selectedAvatar;

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
		setLayout(new BorderLayout());

		
		JPanel topPanel = new JPanel(new BorderLayout());
		ImageIcon logo = new ImageIcon(getClass().getResource("/logo.png"));
		Image img = logo.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
		ImageIcon resizedLogo = new ImageIcon(img);
		JLabel logoLabel = new JLabel(resizedLogo);
		logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(logoLabel, BorderLayout.NORTH);
		add(topPanel, BorderLayout.NORTH);

		
		JPanel panelCentro = new JPanel(new GridBagLayout());
		panelCentro.setBorder(BorderFactory.createEmptyBorder(1, 50, 1, 50));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.weightx = 1.0;

		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		panelCentro.add(new JLabel("Usuario"), gbc);

		gbc.gridy = 1;
		usernameField = new JTextField();
		panelCentro.add(usernameField, gbc);

		gbc.gridy = 2;
		panelCentro.add(new JLabel("Email"), gbc);

		gbc.gridy = 3;
		emailField = new JTextField();
		panelCentro.add(emailField, gbc);

		gbc.gridy = 4;
		panelCentro.add(new JLabel("Contraseña"), gbc);

		gbc.gridy = 5;
		passwordField = new JPasswordField();
		panelCentro.add(passwordField, gbc);

		
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.CENTER;

		JPanel avatarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		selectedAvatar = new ImageIcon(getClass().getResource("/user.png"));
		selectedAvatar.setDescription(getClass().getResource("/user.png").getPath()); 
		genericAvatarLabel = new JLabel();
		genericAvatarLabel
				.setIcon(new ImageIcon(selectedAvatar.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH))); 
																													
																													
		avatarPanel.add(genericAvatarLabel);

		selectAvatarButton = new JButton("Seleccionar");
		avatarPanel.add(selectAvatarButton);

		panelCentro.add(avatarPanel, gbc);

		add(panelCentro, BorderLayout.CENTER);

		
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

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public JButton getCreateAccountButton() {
		return createAccountButton;
	}

	public JButton getSelectAvatarButton() {
		return selectAvatarButton;
	}

	public void setAvatar(ImageIcon avatar) {
		this.selectedAvatar = avatar;
		genericAvatarLabel.setIcon(new ImageIcon(avatar.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH))); 
																													
																													
	}

	public ImageIcon getAvatar() {
		return selectedAvatar;
	}
}
