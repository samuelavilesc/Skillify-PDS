package com.pds.skillify.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.Arrays;
import java.util.Objects;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("reorder_letters")
public class ReorderLettersQuestion extends Question {

    @Column(nullable = false)
    private String correctAnswer;

    // Constructor por defecto requerido por JPA
    public ReorderLettersQuestion() {}

    public ReorderLettersQuestion(String statement, String correctAnswer, Course course) {
        super(statement, course);
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean checkAnswer(String answer) {
        char[] answerArray = answer.trim().toCharArray();
        char[] correctArray = correctAnswer.toCharArray();

        Arrays.sort(answerArray);
        Arrays.sort(correctArray);

        return Arrays.equals(answerArray, correctArray);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false; // Llama a equals de la superclase

        ReorderLettersQuestion that = (ReorderLettersQuestion) o;
        return Objects.equals(correctAnswer, that.correctAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), correctAnswer);
    }
}
