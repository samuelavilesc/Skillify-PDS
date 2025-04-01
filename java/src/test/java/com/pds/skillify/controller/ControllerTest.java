package com.pds.skillify.controller;

import com.pds.skillify.model.*;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

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
    private AdapterCourseTest adapterCourse; 

    @BeforeEach
    
    public void setUp() {
        controller = Controller.getInstance();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SkillifyPU");
        adapterCourse = new AdapterCourseTest(emf); 

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
        String uniqueUsername = "test_" + UUID.randomUUID();
        User user = new User(uniqueUsername, "pwd123", uniqueUsername + "@mail.com", new ImageIcon());
        UserCatalog.getInstance().addUser(user);

        boolean loginSuccess = controller.login(uniqueUsername, "pwd123");
        assertTrue(loginSuccess);

        controller.logout();
        assertNull(controller.getCurrentUser());
    }


    @Test
  
    public void testGetUserByEmailAndUsername() {
        String uniqueUsername = "juanito_" + UUID.randomUUID();
        String email = uniqueUsername + "@mail.com";
        controller.registerUser(uniqueUsername, new ImageIcon(), email, "pwd");

        User result = controller.getUserByEmailAndUsername(email, uniqueUsername);
        assertNotNull(result);
        assertEquals(uniqueUsername, result.getUsername());
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

        assertEquals(0, dummyUser.getAnsweredQuestionsInCourse(dummyCourse).size()); 
    }

  
    
    
    
    
    // ---------------------- Búsqueda ----------------------

    @Test
    public void testGetUsersStartingWith() {
        String u1 = "andres_" + UUID.randomUUID();
        String u2 = "ana_" + UUID.randomUUID();
        String u3 = "mario_" + UUID.randomUUID();

        controller.registerUser(u1, new ImageIcon(), u1 + "@mail.com", "pass");
        controller.registerUser(u2, new ImageIcon(), u2 + "@mail.com", "pass");
        controller.registerUser(u3, new ImageIcon(), u3 + "@mail.com", "pass");

        Set<User> result = controller.getUsersStartingWith("a");
        assertTrue(result.stream().allMatch(u -> u.getUsername().startsWith("a")));
        assertFalse(result.contains(controller.getCurrentUser())); 
    }

}
