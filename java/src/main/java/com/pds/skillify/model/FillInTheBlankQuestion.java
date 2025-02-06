package com.pds.skillify.model;

public class FillInTheBlankQuestion extends Question {
	private String correctAnswer;

	public FillInTheBlankQuestion(String statement, String correctAnswer) {
		super(statement);
		this.correctAnswer = correctAnswer;
	}

	@Override
	public boolean checkAnswer(String answer) {
		return correctAnswer.equalsIgnoreCase(answer.trim());
	}
}
