package com.pds.skillify.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = MultipleChoiceQuestion.class, name = "multiple_choice"),
		@JsonSubTypes.Type(value = FillInTheBlankQuestion.class, name = "fill_in_blank"),
		@JsonSubTypes.Type(value = ReorderLettersQuestion.class, name = "reorder_letters") })
public abstract class Question {
	protected String statement;

	// Constructor por defecto para JSON deserialization.
	public Question() {
	}

	public Question(String statement) {
		this.statement = statement;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public abstract boolean checkAnswer(String answer);

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Question question = (Question) o;
		return Objects.equals(statement, question.statement);
	}

	@Override
	public int hashCode() {
		return Objects.hash(statement);
	}
}
