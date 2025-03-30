package com.pds.skillify.ui;

import org.junit.jupiter.api.Test;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JLabelOperator;
import org.netbeans.jemmy.operators.JPasswordFieldOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class LoginWindowTest {

	@Test
	public void testLoginWindowComponentsAreVisible() throws Exception {
		JemmyProperties.setCurrentTimeout("ComponentOperator.WaitComponentTimeout", 10000);

		SwingUtilities.invokeAndWait(() -> {
			LoginWindow window = new LoginWindow(true); // Modo test sin controlador
			window.setVisible(true);
		});

		JFrameOperator frame = new JFrameOperator("Skillify");

		// Campos y botones
		JTextFieldOperator username = new JTextFieldOperator(frame, 0);
		assertNotNull(username);
		
		JTextFieldOperator password = new JTextFieldOperator(frame, 1);
		assertNotNull(password);

		JButtonOperator loginButton = new JButtonOperator(frame, "Login");
		assertNotNull(loginButton);

		JLabelOperator registerLabel = new JLabelOperator(frame, "Regístrate");
		assertNotNull(registerLabel);
		
		JLabelOperator forgotPasswordLabel = new JLabelOperator(frame, "He olvidado mi contraseña");
		assertNotNull(forgotPasswordLabel);
	}
	@Test
	public void testLoginFlow() throws Exception {
	    // Ejecutar en el hilo de Swing y mostrar ventana en modo test
	    SwingUtilities.invokeAndWait(() -> {
	        LoginWindow window = new LoginWindow(true); // Sin lógica de controlador
	        window.setVisible(true);
	    });

	    // Operador de la ventana
	    JFrameOperator window = new JFrameOperator("Skillify");

	    // Escribir usuario
	    JTextFieldOperator userField = new JTextFieldOperator(window, 0);
	    userField.clearText();
	    userField.typeText("pedro_test");

	    // Escribir contraseña
	    JPasswordFieldOperator passField = new JPasswordFieldOperator(window, 0);
	    passField.clearText();
	    passField.typeText("1234");

	    // Pulsar login
	    JButtonOperator loginButton = new JButtonOperator(window, "Login");
	    loginButton.push();

	    // Esperamos que siga visible (no esperamos que se cierre)
	    assertTrue(window.isVisible(), "La ventana debería seguir visible tras pulsar login (funcionalidad actual)");
	}

}
