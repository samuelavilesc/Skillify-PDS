package com.pds.skillify.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("multiple_choice")
public class MultipleChoiceQuestion extends Question {


	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "multiple_choice_options", joinColumns = @JoinColumn(name = "question_id"))
	@Column(name = "option_text", nullable = false)
	private List<String> options;

	@Column(nullable = false)
	private int correctAnswer;

	// Constructor por defecto requerido por JPA
	public MultipleChoiceQuestion() {
	}

	public MultipleChoiceQuestion(String statement, List<String> options, int correctAnswer, Course course) {
		super(statement, course);
		this.options = options;
		this.correctAnswer = correctAnswer;
	}

	@Override
	public boolean checkAnswer(String answer) {
		try {
			int index = Integer.parseInt(answer);
			return index == correctAnswer;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false; // Llama a equals de la superclase

		MultipleChoiceQuestion that = (MultipleChoiceQuestion) o;
		return correctAnswer == that.correctAnswer && Objects.equals(options, that.options);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), options, correctAnswer);
	}
}
