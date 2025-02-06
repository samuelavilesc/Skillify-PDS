package com.pds.skillify.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class User {
	private ImageIcon profilePic;
	private String username;
	private String password;
	private String email;
	private int codigo;
	private List<Course> courses;
	
	public User(String username,String password, String email, ImageIcon profilePic) {
		this.username=username;
		this.password=password;
		this.email=email;
		this.profilePic=profilePic;
		this.codigo=0;
		this.courses=new ArrayList<Course>();
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
	public List<Course> getCourses() {
		return courses;
	}
	public void addCourse(Course course) {
		this.courses.add(course);
	}
}
