package com.pds.skillify.ui;

import com.pds.skillify.model.User;
import org.junit.jupiter.api.Test;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JLabelOperator;

import javax.swing.*;

public class AchievementsWindowTest {

    @Test
    public void testAchievementsWindowOpensAndDisplaysTitle() throws Exception {
        // Crear un usuario ficticio
        User dummyUser = new User("pedro_test", "1234", "test@dummy.com", new ImageIcon());
        
        // Ejecutar la ventana en el hilo EDT
        SwingUtilities.invokeLater(() -> new AchievementsWindow(dummyUser));

        // Operador Jemmy para la ventana
        JFrameOperator window = new JFrameOperator("Skillify");

        // Esperar a que aparezca el título con el nombre del usuario
        JLabelOperator titulo = new JLabelOperator(window, "Logros de pedro_test");

        // Verificar que el título esté presente
        assert titulo.getText().contains("pedro_test");
        TestUtils.waitAndClose(window, 1000);
    }
}
