package com.pds.skillify.model;

import java.util.Arrays;

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
}
