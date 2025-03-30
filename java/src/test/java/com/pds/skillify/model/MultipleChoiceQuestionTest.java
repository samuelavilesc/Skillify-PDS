package com.pds.skillify.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

class MultipleChoiceQuestionTest {

    private MultipleChoiceQuestion question;
    private Course course;
    private List<String> options;

    @BeforeEach
    void setUp() {
        course = new Course("Java Basics", "Java Basics Course");
        options = Arrays.asList("Option A", "Option B", "Option C", "Option D");
        question = new MultipleChoiceQuestion("Which option is correct?", options, 2, course);
    }

    @Test
    void testInitialization() {
        assertEquals("Which option is correct?", question.getStatement());
        assertEquals(options, question.getOptions());
        assertEquals(2, question.getCorrectAnswer());
        assertEquals(course, question.getCourse());
    }

    @Test
    void testCheckAnswer_Correct() {
        assertTrue(question.checkAnswer("2"));
    }

    @Test
    void testCheckAnswer_Incorrect() {
        assertFalse(question.checkAnswer("1"));
        assertFalse(question.checkAnswer("3"));
    }

    @Test
    void testCheckAnswer_InvalidInput() {
        assertFalse(question.checkAnswer("A"));
        assertFalse(question.checkAnswer("Invalid"));
        assertFalse(question.checkAnswer("")); // Vacío
    }

    @Test
    void testSetCorrectAnswer() {
        question.setCorrectAnswer(1);
        assertEquals(1, question.getCorrectAnswer());
        assertTrue(question.checkAnswer("1"));
    }

    @Test
    void testSetOptions() {
        List<String> newOptions = Arrays.asList("X", "Y", "Z");
        question.setOptions(newOptions);
        assertEquals(newOptions, question.getOptions());
    }

    @Test
    void testEquality() {
        MultipleChoiceQuestion sameQuestion = new MultipleChoiceQuestion(
            "Which option is correct?", options, 2, course);
        MultipleChoiceQuestion differentQuestion = new MultipleChoiceQuestion(
            "Which option is correct?", options, 1, course);

        assertEquals(question, sameQuestion);
        assertNotEquals(question, differentQuestion);
    }

    @Test
    void testHashCode() {
        MultipleChoiceQuestion sameQuestion = new MultipleChoiceQuestion(
            "Which option is correct?", options, 2, course);
        assertEquals(question.hashCode(), sameQuestion.hashCode());
    }
}
