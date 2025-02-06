package com.pds.skillify.model;

public class FillInTheBlankQuestion extends Question {
	private String correctAnswer;

	// Constructor por defecto para JSON deserialization.
	public FillInTheBlankQuestion() {
	}

	public FillInTheBlankQuestion(String statement, String correctAnswer) {
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
		return correctAnswer.equalsIgnoreCase(answer.trim());
	}
}
