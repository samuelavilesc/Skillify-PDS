package com.pds.skillify.model;

import java.util.Arrays;
import java.util.Objects;

public class ReorderLettersQuestion extends Question {
	private String correctAnswer;

	// Constructor por defecto para JSON deserialization.
	public ReorderLettersQuestion() {
	}

	public ReorderLettersQuestion(String statement, String correctAnswer) {
		super(statement);
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
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false; // Llama a equals de la superclase

		ReorderLettersQuestion that = (ReorderLettersQuestion) o;
		return Objects.equals(correctAnswer, that.correctAnswer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), correctAnswer);
	}
}
