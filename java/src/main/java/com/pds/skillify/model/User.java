package com.pds.skillify.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

import javax.swing.ImageIcon;
import com.pds.skillify.utils.ImageUtils;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "profile_pic_path")
	private String profilePic;


	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(unique = true, nullable = false)
	private String email;

	/*
	 * Atributos de tiempo de uso.
	 */

	@Column(nullable = false)
	private int currentLoginStreak = 1;

	@Column(nullable = false)
	private int bestLoginStreak = 1;

	@Column(nullable = false)
	private LocalDate lastLoginDate;

	@Column(nullable = false)
	private long activeTimeInSeconds = 0;

	@Transient
	private transient long sessionStartTime; // No persistido

	/**
	 * Mapa que almacena el conjunto de preguntas respondidas por curso.
	 */

	@ElementCollection
	@CollectionTable(name = "user_answered_questions", joinColumns = @JoinColumn(name = "user_id"))
	@MapKeyJoinColumn(name = "course_id")
	@Column(name = "question_id")
	private Map<Course, Set<Integer>> answeredQuestions = new HashMap<>();
	// fundamentos de java , lista de preguntas q ha respondido
	// si la longitud de la lista de preguntas == longitud del curso
	private Set<Long> completedCourses = new HashSet<Long>();

	public void addCompletedCourse(Course course) {
		completedCourses.add(course.getId());
	}

	// Constructor vacío requerido por JPA
	public User() {
	}

	// Constructor para registro
	public User(String username, String password, String email, ImageIcon profilePic) {
		this.username = username;
		this.password = password;
		this.email = email;
		setProfilePic(profilePic); // Convierte la imagen a bytes
		this.lastLoginDate = LocalDate.now();
	}

	/*
	 * Métodos relacionados al tiempo de uso.
	 */

	public void startSession() {
		this.sessionStartTime = System.nanoTime();
	}

	public void endSession() {
		if (sessionStartTime > 0) {
			long elapsedTime = System.nanoTime() - sessionStartTime;
			activeTimeInSeconds += elapsedTime / 1_000_000_000; // Nanosegundos a segundos
			sessionStartTime = 0;
		}
	}

	public void updateLoginStreak() {
		LocalDate today = LocalDate.now();

		if (lastLoginDate == null) {

			currentLoginStreak = 1;
			bestLoginStreak = 1;

		} else if (!lastLoginDate.equals(today)) {

			
			if (lastLoginDate.equals(today.minusDays(1))) {
				currentLoginStreak++;
			} else {
				currentLoginStreak = 1;
			}

			if (currentLoginStreak > bestLoginStreak) {
				bestLoginStreak = currentLoginStreak;
			}
		}

		lastLoginDate = today;
	}

	// 🔹 Métodos para la gestión de cursos y progreso

	/**
	 * Añade un curso al usuario si aún no está registrado.
	 * 
	 * @param course Curso a agregar.
	 */
	public void addCourse(Course course) {
		answeredQuestions.putIfAbsent(course, new HashSet<>());
	}

	/**
	 * Verifica si el usuario ya tiene un curso registrado.
	 * 
	 * @param course Curso a verificar.
	 * @return true si el usuario ya tiene el curso, false en caso contrario.
	 */
	public boolean alreadyHasCourse(Course course) {
		return answeredQuestions.containsKey(course);
	}

	/**
	 * Calcula el progreso del usuario en un curso basado en las preguntas
	 * respondidas.
	 * 
	 * @param course Curso en el que se quiere conocer el progreso.
	 * @return Progreso en porcentaje (0-100).
	 */
	public int getCourseProgress(Course course) {
		if (!answeredQuestions.containsKey(course) || course.getQuestions().isEmpty()) {
			return 0;
		}
		int answeredCount = answeredQuestions.get(course).size();
		int totalQuestions = course.getQuestions().size();
		return (int) ((answeredCount / (double) totalQuestions) * 100);
	}

	/**
	 * Obtiene todos los cursos en los que el usuario ha respondido preguntas.
	 * 
	 * @return Set de cursos.
	 */
	public Set<Course> getAllCourses() {
		return answeredQuestions.keySet();
	}

	/**
	 * Registra una pregunta como respondida en un curso.
	 * 
	 * @param course   Curso en el que se respondió la pregunta.
	 * @param question Pregunta respondida.
	 */
	public void addAnsweredQuestion(Course course, Question question) {
		answeredQuestions.computeIfAbsent(course, k -> new HashSet<>()).add(question.getId());
	}

	/**
	 * Verifica si el usuario ha respondido una pregunta en un curso.
	 * 
	 * @param course   Curso en el que se encuentra la pregunta.
	 * @param question Pregunta a verificar.
	 * @return true si la pregunta fue respondida, false en caso contrario.
	 */
	public boolean hasAnsweredQuestion(Course course, Question question) {
		return answeredQuestions.getOrDefault(course, Collections.emptySet()).contains(question.getId());
	}

	/**
	 * Obtiene todas las preguntas respondidas en un curso específico.
	 * 
	 * @param course Curso en el que se buscan las preguntas respondidas.
	 * @return Set de preguntas respondidas.
	 */
	public Set<Integer> getAnsweredQuestionsInCourse(Course course) {
		return answeredQuestions.getOrDefault(course, Collections.emptySet());
	}

	// 🔹 Métodos para la gestión de la imagen de perfil
	public ImageIcon getProfilePic() {
		return ImageUtils.pathToImageIcon(profilePic);
	}

	public void setProfilePic(ImageIcon profilePic) {
		this.profilePic = ImageUtils.imageIconToPath(profilePic);
	}

	// 🔹 Getters y Setters
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getActiveTimeInSeconds() {
		return activeTimeInSeconds;
	}

	public long getActiveTimeInHours() {
		return activeTimeInSeconds / 3600;
	}

	public void setActiveTimeInSeconds(long activeTimeInSeconds) {
		this.activeTimeInSeconds = activeTimeInSeconds;
	}

	public int getCurrentLoginStreak() {
		return currentLoginStreak;
	}

	public void setCurrentLoginStreak(int currentLoginStreak) {
		this.currentLoginStreak = currentLoginStreak;
	}

	
	public int getBestLoginStreak() {
		return bestLoginStreak;
	}
	

	public void setBestLoginStreak(int bestLoginStreak) {
		this.bestLoginStreak = bestLoginStreak;
	}
	

	public LocalDate getLastLoginDate() {
		return lastLoginDate;
	}
	

	public void setLastLoginDate(LocalDate lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	
	public Set<Long> getCompletedCourses() {
		return Collections.unmodifiableSet(completedCourses);
	}

	public void resetCourseProgress(Course course) {
		if (answeredQuestions.containsKey(course)) {
			answeredQuestions.get(course).clear();
		}
	}
	

}
