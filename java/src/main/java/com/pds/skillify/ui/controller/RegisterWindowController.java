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


	private void handleClickOnCreateAccount(JButton createAccountButton) {
		createAccountButton.addActionListener(e -> registerUser());
	}

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
		ImageIcon avatar = view.getAvatar(); // Obtener el avatar seleccionado

		// Validaciones antes de llamar a Controller
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

		// Intentar registrar usuario
		boolean success = Controller.getInstance().registerUser(username, avatar, email, password);

		if (!success) {
			JOptionPane.showMessageDialog(view, "El nombre de usuario o email ya están registrados.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Si todo es correcto, abrir MainWindow y cerrar RegisterWindow
		JOptionPane.showMessageDialog(view, "Registro exitoso. Bienvenido a Skillify!", "Éxito",
				JOptionPane.INFORMATION_MESSAGE);
		new MainWindow();
		view.dispose();
	}

	/**
	 * Valida que el email tenga el formato correcto.
	 */
	private boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
		return email.matches(emailRegex);
	}
}
