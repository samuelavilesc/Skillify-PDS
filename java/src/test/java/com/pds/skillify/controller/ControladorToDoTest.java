package com.pds.skillify.controller;

import com.pds.skillify.model.*;
import com.pds.skillify.utils.CourseJSONUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.ImageIcon;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ControladorToDoTest {

    @Mock
    private UserCatalog userCatalog;

    @Mock
    private CourseCatalog courseCatalog;

    @Mock
    private CourseJSONUtils courseJSONUtils;

    @InjectMocks
    private Controller controller;

    private User dummyUser;
    private Course dummyCourse;
    private FillInTheBlankQuestion dummyQuestion;

    @BeforeEach
    public void setUp() {
        dummyUser = new User("mockUser", "pwd", "mock@mail.com", new ImageIcon());
        dummyCourse = new Course("Curso Mock", "Desc");
        dummyQuestion = new FillInTheBlankQuestion("Pregunta?", "Respuesta", dummyCourse);
    }

    @Test
    public void testLoginSuccess() {
        when(userCatalog.getUser("mockUser")).thenReturn(dummyUser);

        boolean result = controller.login("mockUser", "pwd");

        assertTrue(result);
        assertEquals(dummyUser, controller.getCurrentUser());
    }

    @Test
    public void testLoginFail() {
        when(userCatalog.getUser("wrong")).thenReturn(null);

        boolean result = controller.login("wrong", "nopass");

        assertFalse(result);
    }

    @Test
    public void testRegisterUserSuccess() {
        when(userCatalog.getUser("mockUser"))
                .thenReturn(null) 
                .thenReturn(dummyUser); 

        when(userCatalog.existsUser(any())).thenReturn(false);
        doNothing().when(userCatalog).addUser(any());

        boolean registered = controller.registerUser("mockUser", new ImageIcon(), "mock@mail.com", "pwd");

        assertTrue(registered);
    }

    @Test
    public void testUpdateUserPassword() {
        controller.setCurrentUser(dummyUser);
        controller.updateUserPassword(dummyUser, "newPwd");

        assertEquals("newPwd", dummyUser.getPassword());
    }

    @Test
    public void testGetUserByEmailAndUsername() {
        User matchUser = new User("mockUser", "123", "mock@mail.com", new ImageIcon());
        when(userCatalog.getUsers()).thenReturn(List.of(matchUser));

        User result = controller.getUserByEmailAndUsername("mock@mail.com", "mockUser");

        assertNotNull(result);
    }

    @Test
    public void testGetUsersStartingWith_excludesCurrentUser() {
        User another = new User("ana", "pwd", "ana@mail.com", new ImageIcon());
        controller.setCurrentUser(dummyUser);
        when(userCatalog.getUsersStartingWith("a")).thenReturn(new HashSet<>(Set.of(dummyUser, another)));

        Set<User> result = controller.getUsersStartingWith("a");

        assertEquals(1, result.size());
        assertTrue(result.contains(another));
        assertFalse(result.contains(dummyUser));
    }
}