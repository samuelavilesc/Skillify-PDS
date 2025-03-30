package com.pds.skillify.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JListOperator;

import com.pds.skillify.controller.Controller;
import com.pds.skillify.model.User;

import org.netbeans.jemmy.operators.JFrameOperator;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.SwingUtilities;

public class MainWindowTest {

    @BeforeEach
    public void setup() {
        JemmyProperties.setCurrentTimeout("ComponentOperator.WaitComponentTimeout", 10000);
    }

    @Test
    public void testMainWindowComponentsVisible() throws Exception {
        // Crear usuario falso
        User fakeUser = new User();
        fakeUser.setUsername("Pedro");
        fakeUser.setEmail("pedro@example.com");

        // Inyectarlo en el controlador singleton
        Controller.getInstance().setCurrentUser(fakeUser);

        SwingUtilities.invokeAndWait(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });

        JFrameOperator frame = new JFrameOperator("Skillify");

        assertNotNull(new JButtonOperator(frame, "Importar curso"));
        assertNotNull(new JListOperator(frame));
        // Aquí puedes seguir comprobando iconos si los necesitas
    }


}
