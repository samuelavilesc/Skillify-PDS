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
			LoginWindow window = new LoginWindow(true); 
			window.setVisible(true);
		});

		JFrameOperator frame = new JFrameOperator("Skillify");

	
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
	    
	    SwingUtilities.invokeAndWait(() -> {
	        LoginWindow window = new LoginWindow(true); 
	        window.setVisible(true);
	    });

	    
	    JFrameOperator window = new JFrameOperator("Skillify");

	    
	    JTextFieldOperator userField = new JTextFieldOperator(window, 0);
	    userField.clearText();
	    userField.typeText("pedro_test");

	    
	    JPasswordFieldOperator passField = new JPasswordFieldOperator(window, 0);
	    passField.clearText();
	    passField.typeText("1234");

	   
	    JButtonOperator loginButton = new JButtonOperator(window, "Login");
	    loginButton.push();

	   
	    assertTrue(window.isVisible(), "La ventana debería seguir visible tras pulsar login (funcionalidad actual)");
	}

}
