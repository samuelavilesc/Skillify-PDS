package com.pds.skillify.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;

public class User {

	private ImageIcon profilePic;
	private String username;
	private String password;
	private String email;
	private int codigo;
	private Map<Course, Integer> coursesToProgress;
	
	private final static int PROGRESS_FOR_NON_EXISTANT_COURSE = -1;

	// Constructor para registro
	public User(String username, String password, String email, ImageIcon profilePic) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.profilePic = profilePic;
		this.codigo = 0;
		this.coursesToProgress = new HashMap<Course, Integer>();
	}

	public void addCourse(Course course) {
		if (!alreadyHasCourse(course)) {
			coursesToProgress.put(course, 0);
		}
	}

	public boolean alreadyHasCourse(Course course) {
		return coursesToProgress.containsKey(course);
	}

	public void updateCourseProgress(Course course, int progress) {
		if (progress < 0 || progress > 100) {
			throw new IllegalArgumentException("El progreso debe estar entre 0 y 100.");
		}
		coursesToProgress.put(course, progress);
	}

	public int getCourseProgress(Course course) {
		return coursesToProgress.getOrDefault(course, PROGRESS_FOR_NON_EXISTANT_COURSE);
	}

	public Set<Course> getAllCourses() {
		return new HashSet<>(coursesToProgress.keySet());
	}

	public ImageIcon getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(ImageIcon profilePic) {
		this.profilePic = profilePic;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

}
