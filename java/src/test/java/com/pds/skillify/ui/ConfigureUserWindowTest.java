package com.pds.skillify.ui;

import com.pds.skillify.controller.Controller;
import com.pds.skillify.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JLabelOperator;
import org.netbeans.jemmy.operators.JPasswordFieldOperator;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigureUserWindowTest {

    @BeforeEach
    public void setup() {
        JemmyProperties.setCurrentTimeout("ComponentOperator.WaitComponentTimeout", 10000);

       
        User fakeUser = new User();
        fakeUser.setUsername("Pedro");
        fakeUser.setEmail("pedro@example.com");
        Controller.getInstance().setCurrentUser(fakeUser);
    }

    @Test
    public void testConfigureUserWindowComponentsVisible() throws Exception {
        SwingUtilities.invokeAndWait(() -> new ConfigureUserWindow());

        JFrameOperator frame = new JFrameOperator("Skillify");

       
        JLabelOperator usernameLabel = new JLabelOperator(frame, "Pedro");
        assertNotNull(usernameLabel);

        
        JLabelOperator emailLabel = new JLabelOperator(frame, "pedro@example.com");
        assertNotNull(emailLabel);

       
        JPasswordFieldOperator passwordField = new JPasswordFieldOperator(frame);
        assertNotNull(passwordField);

        
        JButtonOperator saveButton = new JButtonOperator(frame, "Guardar");
        assertNotNull(saveButton);
    }

    @Test
    public void testChangePasswordAndClickSave() throws Exception {
        SwingUtilities.invokeAndWait(() -> new ConfigureUserWindow());

        JFrameOperator frame = new JFrameOperator("Skillify");
        JPasswordFieldOperator passwordField = new JPasswordFieldOperator(frame);
        JButtonOperator saveButton = new JButtonOperator(frame, "Guardar");

        
        passwordField.clearText();
        passwordField.typeText("nuevaPassword123");
        saveButton.push();

        
        assertTrue(passwordField.getPassword().length > 0);
    }
} 
