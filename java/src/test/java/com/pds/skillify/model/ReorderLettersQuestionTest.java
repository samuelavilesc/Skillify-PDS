package com.pds.skillify.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReorderLettersQuestionTest {

    private ReorderLettersQuestion question;
    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course("Anagram", "Anagram Training");
        question = new ReorderLettersQuestion("Rearrange the letters to form a fruit: 'elppa'", "apple", course);
    }

    @Test
    void testInitialization() {
        assertEquals("Rearrange the letters to form a fruit: 'elppa'", question.getStatement());
        assertEquals("apple", question.getCorrectAnswer());
        assertEquals(course, question.getCourse());
    }

    @Test
    void testCheckAnswer_Correct() {
        assertTrue(question.checkAnswer("ppale"));
        assertTrue(question.checkAnswer("apple"));
    }

    @Test
    void testCheckAnswer_CorrectWithWhitespace() {
        assertTrue(question.checkAnswer("  elppa "));
    }

    @Test
    void testCheckAnswer_Incorrect() {
        assertFalse(question.checkAnswer("banana"));
        assertFalse(question.checkAnswer("appl")); // Falta una letra
    }

    @Test
    void testSetCorrectAnswer() {
        question.setCorrectAnswer("banana");
        assertEquals("banana", question.getCorrectAnswer());
        assertTrue(question.checkAnswer("nnaaba"));
    }

    @Test
    void testEquality() {
        ReorderLettersQuestion sameQuestion = new ReorderLettersQuestion(
            "Rearrange the letters to form a fruit: 'elppa'", "apple", course);
        ReorderLettersQuestion differentQuestion = new ReorderLettersQuestion(
            "Rearrange the letters to form a fruit: 'ananab'", "banana", course);

        assertEquals(question, sameQuestion);
        assertNotEquals(question, differentQuestion);
    }

    @Test
    void testHashCode() {
        ReorderLettersQuestion sameQuestion = new ReorderLettersQuestion(
            "Rearrange the letters to form a fruit: 'elppa'", "apple", course);
        assertEquals(question.hashCode(), sameQuestion.hashCode());
    }
}
