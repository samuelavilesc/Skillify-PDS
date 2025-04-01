package com.pds.skillify.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.ImageIcon;
import java.time.LocalDate;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "password123", "test@example.com", new ImageIcon());
    }

    @Test
    void testUserInitialization() {
        assertEquals("testUser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("test@example.com", user.getEmail());
        assertNotNull(user.getLastLoginDate());
        assertEquals(1, user.getCurrentLoginStreak());
        assertEquals(1, user.getBestLoginStreak());
    }

    @Test
    void testStartAndEndSession() throws InterruptedException {
        user.startSession();
        Thread.sleep(1000);
        user.endSession();

        assertTrue(user.getActiveTimeInSeconds() >= 1, "Active time should be at least 1 second");
    }

    @Test
    void testUpdateLoginStreak() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        user.setLastLoginDate(yesterday);
        user.updateLoginStreak();

        assertEquals(2, user.getCurrentLoginStreak());
        assertEquals(2, user.getBestLoginStreak());
    }

    @Test
    void testResetLoginStreak() {
        user.setLastLoginDate(LocalDate.now().minusDays(2));
        user.updateLoginStreak();

        assertEquals(1, user.getCurrentLoginStreak());
    }

    @Test
    void testAddCompletedCourse() {
        Course course = new Course("Advanced Java", "Advanced Java Course");
        user.addCompletedCourse(course);

        assertTrue(user.getCompletedCourses().contains(course.getId()));
    }

    @Test
    void testResetCourseProgress() {
    	Course course = new Course("Algorithms", "Algorithms Course");
    	FillInTheBlankQuestion q1 = new FillInTheBlankQuestion("What is recursion?", "Answer", course);
        user.addCourse(course);
        user.addAnsweredQuestion(course, q1);

        assertFalse(user.getAnsweredQuestionsInCourse(course).isEmpty());

        user.resetCourseProgress(course);
        assertTrue(user.getAnsweredQuestionsInCourse(course).isEmpty());
    }
}
