package com.pds.skillify.ui.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import com.pds.skillify.controller.Controller;
import com.pds.skillify.ui.LoginWindow;
import com.pds.skillify.ui.MainWindow;
import com.pds.skillify.ui.RegisterWindow;
import java.io.File;

public class RegisterWindowController {
	private RegisterWindow view;

	public RegisterWindowController(RegisterWindow view) {
		this.view = view;
		initializeControllers();
	}

	private void initializeControllers() {
		handleClickOnSelectAvatar(view.getSelectAvatarButton());
		handleClickOnCreateAccount(view.getCreateAccountButton());
		handleClickOnLogin(view.getLoginLabel());
	}
	/**
	 * Añade un ActionListener al botón de selección de avatar.
	 * Al hacer clic, se abre un selector de archivos para elegir una imagen PNG o JPG,
	 * y si es válida, se actualiza el avatar en la interfaz.
	 *
	 * @param selectAvatarButton Botón que permite seleccionar una nueva imagen de avatar.
	 */
	private void handleClickOnSelectAvatar(JButton selectAvatarButton) {
		selectAvatarButton.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Seleccionar Avatar");
			fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
				"Archivos de imagen (*.jpg, *.png)", "jpg", "png"));

			int seleccion = fileChooser.showOpenDialog(null);
			if (seleccion == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				String fileName = selectedFile.getName().toLowerCase();

				if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
					ImageIcon newAvatar = new ImageIcon(selectedFile.getAbsolutePath());
					newAvatar.setDescription(selectedFile.getAbsolutePath());
					view.setAvatar(newAvatar); // Actualiza la imagen en la interfaz
				} else {
					JOptionPane.showMessageDialog(null,
						"Solo se permiten imágenes en formato PNG,JPEG o JPG.",
						"Formato no válido",
						JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Añade un ActionListener al botón de creación de cuenta.
	 * Al hacer clic, se ejecuta el método {@code registerUser()} para registrar un nuevo usuario.
	 *
	 * @param createAccountButton Botón que inicia el proceso de registro de una nueva cuenta.
	 */
	private void handleClickOnCreateAccount(JButton createAccountButton) {
		createAccountButton.addActionListener(e -> registerUser());
	}
	
	/**
	 * Añade un MouseListener a la etiqueta de inicio de sesión.
	 * Al hacer clic sobre la etiqueta, se cierra la vista actual y se abre la ventana de inicio de sesión.
	 *
	 * @param loginLabel Etiqueta que permite al usuario acceder a la ventana de login.
	 */
	private void handleClickOnLogin(JLabel loginLabel) {
		loginLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				view.dispose();
				new LoginWindow();
			}
		});
	}
	

	private void registerUser() {
		String username = view.getUsernameField().getText().trim();
		String email = view.getEmailField().getText().trim();
		String password = new String(view.getPasswordField().getPassword()).trim();
		ImageIcon avatar = view.getAvatar(); 

		
		if (!isValidEmail(email)) {
			JOptionPane.showMessageDialog(view, "Ingrese un email válido (nombre@email.dominio).", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		
		if (password.length() < 8) {
			JOptionPane.showMessageDialog(view, "La contraseña debe tener al menos 8 caracteres.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		
		boolean success = Controller.getInstance().registerUser(username, avatar, email, password);

		if (!success) {
			JOptionPane.showMessageDialog(view, "El nombre de usuario o email ya están registrados.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		
		JOptionPane.showMessageDialog(view, "Registro exitoso. Bienvenido a Skillify!", "Éxito",
				JOptionPane.INFORMATION_MESSAGE);
		new MainWindow();
		view.dispose();
	}

	/**
	 * Valida si una cadena de texto tiene un formato de correo electrónico válido.
	 *
	 * @param email Dirección de correo electrónico a validar.
	 * @return {@code true} si el formato del correo es válido; {@code false} en caso contrario.
	 */
	private boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
		return email.matches(emailRegex);
	}
}
