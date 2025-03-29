package com.pds.skillify.ui.controller;

import com.pds.skillify.controller.Controller;
import com.pds.skillify.model.User;
import com.pds.skillify.ui.ForgotPasswordWindow;

public class ForgotPasswordWindowController {

	private final ForgotPasswordWindow window;

	public ForgotPasswordWindowController(ForgotPasswordWindow window) {
		this.window = window;
	}

	public void handlePasswordReset() {
		String email = window.getEmail();
		String username = window.getUsername();
		
		String newPassword = window.getNewPassword();

		if (email.isEmpty() || username.isEmpty() || newPassword.isEmpty()) {
			window.showStatusMessage("Por favor, rellena todos los campos.", false);
			return;
			
		}

		User user = Controller.getInstance().getUserByEmailAndUsername(email, username);

		if (user == null) {
			window.showStatusMessage("Usuario no encontrado.", false);
			return;
		}

		Controller.getInstance().updateUserPassword(user, newPassword);
		window.showStatusMessage("Contraseña actualizada correctamente.", true);

		// Puedes cerrar la ventana automáticamente tras un breve retraso si quieres
		window.closeWindow();
	}
}
