package com.pds.skillify.model;

public abstract class Question {
	
	protected String statement;

	public Question(String statement) {
		this.statement = statement;
	}

	public String getStatement() {
		return statement;
	}

	public abstract boolean checkAnswer(String answer);
}
