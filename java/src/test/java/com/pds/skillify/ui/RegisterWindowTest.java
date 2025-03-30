package com.pds.skillify.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.operators.*;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterWindowTest {

    @BeforeEach
    public void setup() {
        JemmyProperties.setCurrentTimeout("ComponentOperator.WaitComponentTimeout", 10000);
    }

    @Test
    public void testRegisterWindowComponentsAndFlow() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            RegisterWindow window = new RegisterWindow();
            window.setVisible(true);
        });

        JFrameOperator frame = new JFrameOperator("Skillify");

        // Verificar existencia de campos
        JTextFieldOperator usernameField = new JTextFieldOperator(frame, 0);
        JTextFieldOperator emailField = new JTextFieldOperator(frame, 1);
        JPasswordFieldOperator passwordField = new JPasswordFieldOperator(frame, 0);
        JButtonOperator selectAvatarButton = new JButtonOperator(frame, "Seleccionar");
        JButtonOperator createAccountButton = new JButtonOperator(frame, "Crear Cuenta");
        JLabelOperator loginLabel = new JLabelOperator(frame, "Inicia sesión");

        assertNotNull(usernameField);
        assertNotNull(emailField);
        assertNotNull(passwordField);
        assertNotNull(selectAvatarButton);
        assertNotNull(createAccountButton);
        assertNotNull(loginLabel);

        // Simular introducción de datos
        usernameField.clearText();
        usernameField.typeText("nuevo_usuario");
        assertEquals("nuevo_usuario", usernameField.getText());

        emailField.clearText();
        emailField.typeText("nuevo@email.com");
        assertEquals("nuevo@email.com", emailField.getText());

        passwordField.clearText();
        passwordField.typeText("123456");
        assertEquals("123456", new String(passwordField.getPassword()));

        // Simular clics
        selectAvatarButton.push(); // No abre selector real, solo prueba de botón
        createAccountButton.push(); // Debería lanzar acción (no comprobamos resultado aquí)
        loginLabel.clickMouse(); // Simula clic en "Inicia sesión"
    }
}
