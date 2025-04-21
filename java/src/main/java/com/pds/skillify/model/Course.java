package com.pds.skillify.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("serial")
@Entity
@Table(name = "courses")
public class Course implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;
	

	@Lob
	private String description;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Question> questions;
	
	@Transient
	private CourseMode mode = CourseMode.SEQUENTIAL;

	
	
	public Course() {
	}

	public Course(String name, String desc) {
		this.name = name;
		this.description = desc;
	}

	
	public Course(String name, String desc, List<Question> questions) {
		this.name = name;
		this.description = desc;
		this.questions = new ArrayList<>(questions);
	}

	
	public void addQuestion(Question question) {
		questions.add(question);
		question.setCourse(this); 
	}
	

	public void shuffleQuestions() {
		Collections.shuffle(questions);
	}

	public Long getId() {
		return id;
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
		List<Question> copy = new ArrayList<>(questions);
		this.questions = copy;
	}

	public List<Question> getQuestions() {
		List<Question> questionList = new ArrayList<>(questions);

		if (mode == CourseMode.RANDOM) {
			Collections.shuffle(questionList);
		}

		return questionList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		
		if (o == null || getClass() != o.getClass())
			return false;
		Course course = (Course) o;
		return Objects.equals(name, course.name) && Objects.equals(description, course.description)
				&& Objects.equals(questions, course.questions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, description, questions);
	}

	
	public CourseMode getMode() {
		return mode;
	}

	public void setMode(CourseMode mode) {
		this.mode = mode;
	}
	public void setId(Long id) {
	    this.id = id;
	}

}
