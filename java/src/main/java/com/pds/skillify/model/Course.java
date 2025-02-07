package com.pds.skillify.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Course {

	private String name;
	private String description;
	private List<Question> questions;

	// Constructor por defecto necesario para JSON deserialization
	public Course() {
		this.questions = new ArrayList<>();
	}

	public Course(String name, String desc) {
		this.name = name;
		this.description = desc;
		this.questions = new ArrayList<>();
	}

	public Course(String name, String desc, List<Question> questions) {
		this.name = name;
		this.description = desc;
		this.questions = new ArrayList<>(questions);
	}

	public void addQuestion(Question question) {
		questions.add(question);
	}

	public void shuffleQuestions() {
		Collections.shuffle(questions);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Question> getQuestions() {
		return new ArrayList<>(questions);
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name) &&
               Objects.equals(description, course.description) &&
               Objects.equals(questions, course.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, questions);
    }

}
