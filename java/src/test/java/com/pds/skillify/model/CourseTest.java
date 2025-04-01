package com.pds.skillify.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {

    private Course course;
    private MultipleChoiceQuestion q1;
    private MultipleChoiceQuestion q2;

    @BeforeEach
    public void setup() {
        course = new Course("Java Básico", "Curso introductorio");
        q1 = new MultipleChoiceQuestion("¿Qué es Java?", List.of("Lenguaje", "Sistema"), 0, course);
        q2 = new MultipleChoiceQuestion("¿Qué es JVM?", List.of("Máquina", "Lenguaje"), 0, course);

        course.setQuestions(new ArrayList<>());
    }

    
    @Test
    public void testConstructorYGetters() {
        assertEquals("Java Básico", course.getName());
        assertEquals("Curso introductorio", course.getDescription());
    }

    
    @Test
    public void testAddQuestion() {
        course.addQuestion(q1);

        assertEquals(1, course.getQuestions().size());
        assertTrue(course.getQuestions().contains(q1));
        assertEquals(course, q1.getCourse()); 
    }

    @Test
    public void testShuffleQuestions() {
        course.setQuestions(new ArrayList<>(List.of(q1, q2)));

        List<Question> beforeShuffle = new ArrayList<>(course.getQuestions());
        course.shuffleQuestions();
        List<Question> afterShuffle = course.getQuestions();

        assertEquals(2, afterShuffle.size());
        assertTrue(afterShuffle.containsAll(beforeShuffle));
    }
    

    @Test
    public void testGetQuestionsModoRandom() {
        course.setQuestions(List.of(q1, q2));
        course.setMode(CourseMode.RANDOM);

        List<Question> firstCall = course.getQuestions();
        List<Question> secondCall = course.getQuestions();

    
        assertTrue(firstCall.containsAll(secondCall));
        assertEquals(2, firstCall.size());
    }
    

    @Test
    public void testEqualsAndHashCode() {
        Course course2 = new Course("Java Básico", "Curso introductorio");
        course2.setQuestions(List.of());

        course.setQuestions(List.of());

        assertEquals(course, course2);
        assertEquals(course.hashCode(), course2.hashCode());

        course2.setName("Otro nombre");
        assertNotEquals(course, course2);
    }
    
}
