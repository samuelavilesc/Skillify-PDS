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
       
        User dummyUser = new User("pedro_test", "1234", "test@dummy.com", new ImageIcon());
        
       
        SwingUtilities.invokeLater(() -> new AchievementsWindow(dummyUser));

       
        JFrameOperator window = new JFrameOperator("Skillify");

        
        JLabelOperator titulo = new JLabelOperator(window, "Logros de pedro_test");

        
        assert titulo.getText().contains("pedro_test");
        TestUtils.waitAndClose(window, 1000);
    }
}
