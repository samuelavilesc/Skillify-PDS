package com.pds.skillify.ui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.operators.*;

import javax.swing.*;

public class ConfigureUserWindowTest {

    private JFrameOperator window;

    @BeforeEach
    public void setUp() throws Exception {
        // Lanzar la ventana
        new ClassReference(ConfigureUserWindow.class.getName()).startApplication();
        JemmyProperties.setCurrentTimeout("ComponentOperator.WaitComponentTimeout", 5000);

        // Esperar a que la ventana esté lista
        window = new JFrameOperator("Skillify");
    }

    @AfterEach
    public void tearDown() {
        if (window != null) {
            window.dispose();
        }
    }

    @Test
    public void testWindowTitle() {
        assertEquals("Skillify", window.getTitle());
    }

    @Test
    public void testPasswordFieldExistsAndEditable() {
        JPasswordFieldOperator passwordField = new JPasswordFieldOperator(window, 0);
        assertNotNull(passwordField);
        passwordField.clearText();
        passwordField.typeText("nuevaPassword123");
        assertEquals("nuevaPassword123", new String(passwordField.getPassword()));
    }

    @Test
    public void testGuardarButtonExists() {
        JButtonOperator guardarButton = new JButtonOperator(window, "Guardar");
        assertNotNull(guardarButton);
    }

    @Test
    public void testLblUsuarioVisible() {
        JLabelOperator lblUsuario = new JLabelOperator(window, 1); // Está debajo de la imagen
        assertNotNull(lblUsuario);
        assertFalse(lblUsuario.getText().isEmpty());
    }

    @Test
    public void testLblEmailValorVisible() {
        JLabelOperator lblEmailValor = new JLabelOperator(window, 3); // Después del label "Email:"
        assertNotNull(lblEmailValor);
        assertTrue(lblEmailValor.getText().contains("@")); // Básica validación de formato
    }
}
