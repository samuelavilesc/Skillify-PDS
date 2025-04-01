package com.pds.skillify.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FillInTheBlankQuestionTest {

    private FillInTheBlankQuestion question;
    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course("Java Basics", "Java Basics Course");
        question = new FillInTheBlankQuestion("What is the capital of France?", "Paris", course);
    }

    @Test
    void testInitialization() {
        assertEquals("What is the capital of France?", question.getStatement());
        assertEquals("Paris", question.getCorrectAnswer());
        assertEquals(course, question.getCourse());
    }

    @Test
    void testCheckAnswer_CorrectCaseInsensitive() {
        assertTrue(question.checkAnswer("paris"));
        assertTrue(question.checkAnswer("PARIS"));
    }

    @Test
    void testCheckAnswer_WithWhitespace() {
        assertTrue(question.checkAnswer("  Paris  "));
    }

    @Test
    void testCheckAnswer_Incorrect() {
        assertFalse(question.checkAnswer("London"));
        assertFalse(question.checkAnswer("Pariis")); 
    }

    @Test
    void testSetCorrectAnswer() {
        question.setCorrectAnswer("Berlin");
        assertEquals("Berlin", question.getCorrectAnswer());
        assertTrue(question.checkAnswer("berlin"));
    }

    @Test
    void testEquality() {
        FillInTheBlankQuestion sameQuestion = new FillInTheBlankQuestion("What is the capital of France?", "Paris", course);
        FillInTheBlankQuestion differentQuestion = new FillInTheBlankQuestion("What is 2+2?", "4", course);

        assertEquals(question, sameQuestion);
        assertNotEquals(question, differentQuestion);
    }

    @Test
    void testHashCode() {
        FillInTheBlankQuestion sameQuestion = new FillInTheBlankQuestion("What is the capital of France?", "Paris", course);
        assertEquals(question.hashCode(), sameQuestion.hashCode());
    }
}
