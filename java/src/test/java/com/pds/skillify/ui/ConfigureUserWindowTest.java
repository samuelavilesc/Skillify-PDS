package com.pds.skillify.ui;
/*
import com.pds.skillify.controller.Controller;
import com.pds.skillify.model.User;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.netbeans.jemmy.operators.*;

import javax.swing.*;

import static org.mockito.Mockito.*;

public class ConfigureUserWindowTest {

    private static MockedStatic<Controller> mockedController;
    private JFrameOperator frame;

    @BeforeAll
    public static void mockController() {
        mockedController = mockStatic(Controller.class);
        Controller mockCtrl = mock(Controller.class);

        User mockUser = new User();
        mockUser.setUsername("pedro_test");
        mockUser.setEmail("pedro@example.com");

        when(mockCtrl.getCurrentUser()).thenReturn(mockUser);
        mockedController.when(Controller::getInstance).thenReturn(mockCtrl);
    }

    @AfterAll
    public static void closeMock() {
        if (mockedController != null) mockedController.close();
    }

    @BeforeEach
    public void setUp() {
        // Abrimos la ventana manualmente para asegurar orden correcto
        SwingUtilities.invokeLater(() -> {
            ConfigureUserWindow ventana = new ConfigureUserWindow();
            ventana.setVisible(true);
        });

        // Esperamos a que se abra la ventana con el título correcto
        frame = new JFrameOperator("Skillify");
    }

    @Test
    public void testComponentesVisibles() {
        new JLabelOperator(frame, "Nueva Contraseña:");
        new JPasswordFieldOperator(frame);
        new JButtonOperator(frame, "Guardar");
    }

    @AfterEach
    public void tearDown() {
        if (frame != null) frame.close();
    }
}
*/