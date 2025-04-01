package com.pds.skillify.ui.controller;

import com.pds.skillify.controller.Controller;
import com.pds.skillify.model.User;
import com.pds.skillify.ui.ConfigureUserWindow;
import com.pds.skillify.ui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class ConfigureUserWindowController {
	private ConfigureUserWindow view;
	private User actualUser;
	private ImageIcon newProfilePic = null; 

	public ConfigureUserWindowController(ConfigureUserWindow view) {
		this.view = view;
		this.actualUser = Controller.getInstance().getCurrentUser();
		initializeView();
		initializeHandlers();
	}

	private void initializeView() {
		if (actualUser != null) {
			view.getLblUsuario().setText(actualUser.getUsername());
			view.getLblEmailValor().setText(actualUser.getEmail());

			
			ImageIcon avatarIcon;
			if (actualUser.getProfilePic() != null) {
				avatarIcon = new ImageIcon(
						actualUser.getProfilePic().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
			} else {
				avatarIcon = new ImageIcon(getClass().getResource("/user.png"));
			}
			view.getLblImagenPerfil().setIcon(avatarIcon);
		}
	}

	private void initializeHandlers() {
		
		view.getLblImagenPerfil().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Seleccionar imagen de perfil");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
					"Archivos de imagen (*.jpg, *.png)", "jpg", "png"));

				int seleccion = fileChooser.showOpenDialog(null);
				if (seleccion == JFileChooser.APPROVE_OPTION) {
					File archivo = fileChooser.getSelectedFile();
					String fileName = archivo.getName().toLowerCase();

					if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
						newProfilePic = new ImageIcon(archivo.getAbsolutePath());
						newProfilePic.setDescription(archivo.getAbsolutePath());
						Image img = newProfilePic.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
						view.getLblImagenPerfil().setIcon(new ImageIcon(img));
					} else {
						JOptionPane.showMessageDialog(null,
							"Solo se permiten imágenes en formato PNG,JPEG o JPG.",
							"Formato no válido",
							JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		
		view.getBtnGuardar().addActionListener(e -> actualizarUsuario());

		
		view.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new MainWindow();
			}
		});
	}

	/**
	 * Guarda la nueva contraseña y/o imagen de perfil si el usuario hizo cambios.
	 */
	private void actualizarUsuario() {
		String nuevaContrasena = new String(view.getTxtNuevaContrasena().getPassword()).trim();

		
		if (!nuevaContrasena.isEmpty() && nuevaContrasena.length() < 8) {
			JOptionPane.showMessageDialog(view, "La contraseña debe tener al menos 8 caracteres.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		
		if (!nuevaContrasena.isEmpty()) {
			actualUser.setPassword(nuevaContrasena);
			Controller.getInstance().updateCurrentUser();
		}

		
		if (newProfilePic != null) {
			Controller.getInstance().setNewPfP(newProfilePic);
		}

		
		JOptionPane.showMessageDialog(view, "Cambios guardados correctamente.", "Éxito",
				JOptionPane.INFORMATION_MESSAGE);

		
		view.dispose();
		new MainWindow();
	}
}
