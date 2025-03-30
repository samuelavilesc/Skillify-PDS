package com.pds.skillify.controller;

import com.pds.skillify.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.ImageIcon;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    private Controller controller;
    private User dummyUser;
    private Course dummyCourse;
    private FillInTheBlankQuestion dummyQuestion;

    @BeforeEach
    public void setUp() {
        controller = Controller.getInstance();

        dummyUser = new User("testUser", "1234", "test@example.com", new ImageIcon());
        controller.setCurrentUser(dummyUser);

        dummyCourse = new Course("Curso Test", "Descripción");
        dummyQuestion = new FillInTheBlankQuestion("Capital de España", "Madrid", dummyCourse);
        dummyCourse.setQuestions(new ArrayList<>(List.of(dummyQuestion)));

        dummyUser.addCourse(dummyCourse);
    }

    // ---------------------- Usuario ----------------------

    @Test
    public void testSetAndGetCurrentUser() {
        assertEquals(dummyUser, controller.getCurrentUser());
    }

    @Test
    public void testUpdateUserPassword() {
        controller.updateUserPassword(dummyUser, "nueva");
        assertEquals("nueva", dummyUser.getPassword());
    }

    @Test
  
    public void testLoginAndLogout() {
        User user = new User("nuevo", "pwd123", "nuevo@mail.com", new ImageIcon());
        UserCatalog.getInstance().addUser(user);

        boolean loginSuccess = controller.login("nuevo", "pwd123");
        assertTrue(loginSuccess);

        controller.logout();
        assertNull(controller.getCurrentUser());
    }


    @Test
    public void testGetUserByEmailAndUsername() {
        controller.registerUser("juanito", new ImageIcon(), "juan@mail.com", "pwd");
        User result = controller.getUserByEmailAndUsername("juan@mail.com", "juanito");
        assertNotNull(result);
        assertEquals("juanito", result.getUsername());
    }

    // ---------------------- Progreso y Preguntas ----------------------

    @Test
    public void testSetQuestionAsAnsweredAndCheckCompletion() {
        controller.setQuestionAsAnswered(dummyCourse, dummyQuestion);

        assertTrue(controller.wasAnsweredByCurrentUser(dummyCourse, dummyQuestion));
        assertEquals(1, controller.getCurrentUsersAnsweredQuestionsInCourse(dummyCourse));
    }

    @Test
    public void testResetCurrentUserCourseProgress() {
        controller.setQuestionAsAnswered(dummyCourse, dummyQuestion);
        controller.resetCurrentUsersCourseProgress(dummyCourse);

        assertEquals(0, controller.getCurrentUsersAnsweredQuestionsInCourse(dummyCourse));
    }

    @Test
    public void testGetCurrentUsersProgressInCourse() {
        assertEquals(0, controller.getCurrentUsersProgressInCourse(dummyCourse));
        controller.setQuestionAsAnswered(dummyCourse, dummyQuestion);
        assertEquals(100, controller.getCurrentUsersProgressInCourse(dummyCourse));
    }

    @Test
    public void testGetFinishedCourses() {
        controller.setQuestionAsAnswered(dummyCourse, dummyQuestion);
        List<Course> finished = controller.getFinishedCourses();
        assertEquals(1, finished.size());
        assertEquals("Curso Test", finished.get(0).getName());
    }

    // ---------------------- Estadísticas ----------------------

    @Test
    public void testLoginStreaksAndTime() {
        int streak = controller.getCurrentUsersCurrentLoginStreak();
        assertTrue(streak >= 1);

        dummyUser.startSession();
        dummyUser.endSession();

        assertTrue(controller.getCurrentUsersActiveTimeInSeconds() >= 0);
        assertTrue(controller.getCurrentUsersActiveTimeInHours() >= 0);
    }

    @Test
    public void testEndCurrentUserSession() {
        dummyUser.startSession();
        controller.endCurrentUserSession();

        assertEquals(0, dummyUser.getAnsweredQuestionsInCourse(dummyCourse).size()); // sesión cerrada sin cambios
    }

    // ---------------------- Gestión de cursos ----------------------

    
    @Test
    public void testGetCourseNameById() {
        try {
            var field = Course.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(dummyCourse, 99L);

            CourseCatalog.getInstance().addCourse(dummyCourse); 

            assertEquals("Curso Test", controller.getCourseNameById(99L));
        } catch (Exception e) {
            fail("No se pudo establecer el ID del curso.");
        }
    }

    
    // ---------------------- Búsqueda ----------------------

    @Test
    public void testGetUsersStartingWith() {
        controller.registerUser("andres", new ImageIcon(), "andres@mail.com", "pass");
        controller.registerUser("ana", new ImageIcon(), "ana@mail.com", "pass");
        controller.registerUser("mario", new ImageIcon(), "mario@mail.com", "pass");

        Set<User> result = controller.getUsersStartingWith("a");
        assertTrue(result.stream().allMatch(u -> u.getUsername().startsWith("a")));
        assertFalse(result.contains(controller.getCurrentUser())); // no debe incluir al usuario actual
    }
}
