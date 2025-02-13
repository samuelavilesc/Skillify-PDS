package com.pds.skillify.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = MultipleChoiceQuestion.class, name = "multiple_choice"),
		@JsonSubTypes.Type(value = FillInTheBlankQuestion.class, name = "fill_in_blank"),
		@JsonSubTypes.Type(value = ReorderLettersQuestion.class, name = "reorder_letters") })
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Usa una sola tabla para todas las preguntas
@DiscriminatorColumn(name = "question_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "questions")
public abstract class Question implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	protected String statement;

	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false) // No puede ser nulo
	private Course course;

	// Constructor por defecto requerido por JPA
	public Question() {
	}

	public Question(String statement, Course course) {
		this.statement = statement;
		this.course = course;
	}

	public int getId() {
		return id;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
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
