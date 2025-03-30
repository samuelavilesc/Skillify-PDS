package com.pds.skillify.ui;

import com.pds.skillify.controller.Controller;
import com.pds.skillify.model.User;
import com.pds.skillify.ui.controller.ConfigureUserWindowController;

import javax.swing.*;
import java.awt.*;

public class ConfigureUserWindow extends JFrame {
	private static final long serialVersionUID = 5433790596127130763L;
	private JLabel lblImagenPerfil, lblUsuario, lblEmail, lblEmailValor, lblNuevaContrasena;
	private JPasswordField txtNuevaContrasena;
	private JButton btnGuardar;
	private static final int WIDTH = 370;
	private static final int HEIGHT = 480;

	public ConfigureUserWindow() {
		initialize();
		new ConfigureUserWindowController(this);
		setVisible(true);
	}
	

	private void initialize() {
		setTitle("Skillify");
		setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 10, 5, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL; // Para expandir el botón

		User currentUser = Controller.getInstance().getCurrentUser();

		// Imagen de perfil
		ImageIcon avatarIcon;
		if (currentUser != null && currentUser.getProfilePic() != null) {
			avatarIcon = new ImageIcon(
					currentUser.getProfilePic().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		} else {
			avatarIcon = new ImageIcon(getClass().getResource("/user.png"));
		}

		lblImagenPerfil = new JLabel(avatarIcon);
		lblImagenPerfil.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Avatar centrado
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add(lblImagenPerfil, gbc);

		// Nombre del usuario
		gbc.gridy = 1;
		lblUsuario = new JLabel(currentUser != null ? currentUser.getUsername() : "Usuario", SwingConstants.CENTER);
		lblUsuario.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblUsuario, gbc);

		// Email - Etiqueta
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		lblEmail = new JLabel("Email:");
		add(lblEmail, gbc);

		// Email - Valor
		gbc.gridy = 3;
		lblEmailValor = new JLabel(currentUser != null ? currentUser.getEmail() : "email@dominio.com");
		lblEmailValor.setFont(new Font("Arial", Font.PLAIN, 14));
		add(lblEmailValor, gbc);

		// Nueva contraseña - Etiqueta
		gbc.gridy = 4;
		lblNuevaContrasena = new JLabel("Nueva Contraseña:");
		add(lblNuevaContrasena, gbc);

		// Campo de contraseña
		gbc.gridy = 5;
		txtNuevaContrasena = new JPasswordField(20);
		add(txtNuevaContrasena, gbc);

		// Botón de Guardar centrado
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER; // Centramos el botón
		gbc.fill = GridBagConstraints.NONE; // Evita que se expanda
		btnGuardar = new JButton("Guardar");
		btnGuardar.setPreferredSize(new Dimension(120, 30));
		add(btnGuardar, gbc);
	}

	// Getters
	public JLabel getLblImagenPerfil() {
		return lblImagenPerfil;
	}

	public JLabel getLblUsuario() {
		return lblUsuario;
	}

	public JLabel getLblEmailValor() {
		return lblEmailValor;
	}

	public JPasswordField getTxtNuevaContrasena() {
		return txtNuevaContrasena;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}
	public static void main(String[] args) {
	    SwingUtilities.invokeLater(ConfigureUserWindow::new);
	}

}
