package com.pds.skillify.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Course {

	private static final ObjectMapper objectMapper = new ObjectMapper();

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

	public static Course loadCourseFromJson(String filename) {
	    try {
	        InputStream inputStream = Course.class.getClassLoader().getResourceAsStream(filename);
	        if (inputStream == null) {
	            throw new FileNotFoundException("El archivo no se encontró.");
	        }
	        return objectMapper.readValue(inputStream, Course.class);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
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

}
