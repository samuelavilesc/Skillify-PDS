package com.pds.skillify.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Course {

	private String name;
	private String description;
	private List<Question> questions;

	public Course(String name, String desc) {
		this.name = name;
		this.description = desc;
		this.questions = new ArrayList<>();
	}
	
	public Course(String name, List<Question> questions) {
        this.name = name;
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

	public String getDescription() {
		return description;
	}
	
	public List<Question> getQuestions() {
        return new ArrayList<>(questions);
    }

}
