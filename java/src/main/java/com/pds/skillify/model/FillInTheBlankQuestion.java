package com.pds.skillify.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Objects;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("fill_in_blank")
public class FillInTheBlankQuestion extends Question {

	@Column(nullable = false)
	private String correctAnswer;

	// Constructor por defecto requerido por JPA
	public FillInTheBlankQuestion() {
	}

	public FillInTheBlankQuestion(String statement, String correctAnswer, Course course) {
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
		return correctAnswer.equalsIgnoreCase(answer.trim());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;

		FillInTheBlankQuestion that = (FillInTheBlankQuestion) o;
		return Objects.equals(correctAnswer, that.correctAnswer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), correctAnswer);
	}
}
