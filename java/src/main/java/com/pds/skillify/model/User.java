package com.pds.skillify.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;

import com.pds.skillify.utils.ImageUtils;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


	@Lob // Guardamos la imagen como bytes en la BD
    private byte[] profilePic;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

   
    @ElementCollection
    @CollectionTable(name = "user_progress", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyJoinColumn(name = "course_id")
    @Column(name = "progress")
    private Map<Course, Integer> coursesToProgress = new HashMap<>();

    @Transient
    private final static int PROGRESS_FOR_NON_EXISTANT_COURSE = -1;

    // Constructor vacío requerido por JPA
    public User() {}

    // Constructor para registro
    public User(String username, String password, String email, ImageIcon profilePic) {
        this.username = username;
        this.password = password;
        this.email = email;
        setProfilePic(profilePic); // Convierte la imagen a bytes
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
       // return new HashSet<>(coursesToProgress.keySet());
    	return new HashSet<>();
    }

    public ImageIcon getProfilePic() {
        return ImageUtils.bytesToImageIcon(profilePic);
    }

    public void setProfilePic(ImageIcon profilePic) {
        this.profilePic = ImageUtils.imageIconToBytes(profilePic);
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

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
