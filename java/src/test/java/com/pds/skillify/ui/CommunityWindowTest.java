package com.pds.skillify.ui;

import org.junit.jupiter.api.*;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.operators.*;


import java.util.Set;

import javax.swing.ImageIcon;

import com.pds.skillify.model.User;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommunityWindowTest {

    private JFrameOperator mainWindow;

    @BeforeAll
    public void setUp() throws Exception {
        new ClassReference(CommunityWindow.class.getName()).startApplication();
        mainWindow = new JFrameOperator("Skillify");
    }

    @Test
    public void testWindowTitle() {
        Assertions.assertEquals("Skillify", mainWindow.getTitle());
    }

    @Test
    public void testUsernameFieldExists() {
        JTextFieldOperator field = new JTextFieldOperator(mainWindow);
        Assertions.assertTrue(field.isEnabled());
        field.clearText();
        field.typeText("Pedro");
        Assertions.assertEquals("Pedro", field.getText());
    }

    @Test
    public void testUsersListExists() {
        JListOperator list = new JListOperator(mainWindow);
        Assertions.assertNotNull(list);
        Assertions.assertTrue(list.isEnabled());
    }

    @Test
    public void testUpdateUserList() {
        // Creamos un usuario falso para comprobar que se actualiza la lista
        CommunityWindow window = (CommunityWindow) mainWindow.getSource();
        User fakeUser = new User();
        fakeUser.setUsername("usuario_test");
        window.updateUserList(Set.of(fakeUser));
        ImageIcon defaultPic = new ImageIcon(getClass().getResource("/user.png"));
        fakeUser.setProfilePic(defaultPic);
        JListOperator list = new JListOperator(mainWindow);
        Assertions.assertEquals(1, list.getModel().getSize());
        Assertions.assertEquals("usuario_test", ((User) list.getModel().getElementAt(0)).getUsername());
    }

    @AfterAll
    public void tearDown() {
        mainWindow.dispose();
    }
}
