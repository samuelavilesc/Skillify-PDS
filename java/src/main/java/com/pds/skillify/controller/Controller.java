package com.pds.skillify.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.ImageIcon;
import com.pds.skillify.model.*;
import com.pds.skillify.utils.CourseJSONUtils;

public class Controller {
	private UserCatalog userCatalog;
	private CourseCatalog courseCatalog;

	private static Controller uniqueInstance = null;

	private User currentUser;

	/**
	 * Constructor privado del controlador. Inicializa los catálogos de datos.
	 */
	private Controller() {
		initializeCatalogs();
	}

	/**
	 * Obtiene la instancia única del controlador (Singleton).
	 *
	 * @return Instancia única de `Controller`.
	 */
	public static Controller getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Controller();
		}
		return uniqueInstance;
	}
	private boolean isCompletedCourse(Course course) {
		return getAnsweredQuestions(course) == course.getQuestions().size();
	}

	/**
	 * Inicializa los catálogos de usuarios, cursos y preguntas cargando datos desde
	 * la base de datos.
	 */
	private void initializeCatalogs() {
		this.userCatalog = UserCatalog.getInstance();
		this.courseCatalog = CourseCatalog.getInstance();

	}

	/**
	 * Obtiene los cursos finalizados del usuario actual.
	 *
	 * @return Lista de cursos completados.
	 */
	public List<Course> getFinishedCourses() {
		List<Course> finishedCourses = new ArrayList<>();
		for (Course course : currentUser.getAllCourses()) {
			if (currentUser.getCourseProgress(course) == 100) {
				finishedCourses.add(course);
			}
		}
		return finishedCourses;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User user) {
		this.currentUser = user;
	}

	/**
	 * Añade un curso al usuario actual cargándolo desde un archivo JSON.
	 *
	 * @param filePath Ruta del archivo JSON con el curso.
	 */
	public void addCourseToCurrentUser(String filePath) {
		Course newCourse = CourseJSONUtils.loadCourseFromJson(filePath);

		if (currentUser == null) {
			throw new IllegalStateException("No hay usuario autenticado.");
		}

		// Buscar si el curso ya existe en la base de datos
		Course existingCourse = courseCatalog.getCourse(newCourse.getName());

		if (existingCourse == null) {
			// Si el curso no existe, guardarlo en la BD antes de asignarlo
			courseCatalog.addCourse(newCourse);
			existingCourse = newCourse;
		}

		// Verificar que el usuario no tenga ya este curso
		if (!currentUser.alreadyHasCourse(existingCourse)) {
			currentUser.addCourse(existingCourse);
			userCatalog.updateUser(currentUser);
		}
	}

	/**
	 * Obtiene todos los cursos del usuario actual.
	 *
	 * @return Un conjunto con los cursos del usuario actual.
	 */
	public Set<Course> getAllCurrentUserCourses() {
		return currentUser.getAllCourses();
	}

	/**
	 * Obtiene el progreso del usuario actual en un curso específico.
	 *
	 * @param course Curso a consultar.
	 * @return Progreso del usuario en el curso (0-100).
	 */
	public int getCurrentUsersProgressInCourse(Course course) {
		return currentUser.getCourseProgress(course);
	}

	/**
	 * Verifica si el usuario actual ya tiene un curso específico.
	 *
	 * @param filePath Ruta del archivo JSON con el curso.
	 * @return `true` si el usuario ya tiene el curso, `false` en caso contrario.
	 */
	public boolean currentUserAlreadyHasCourse(String filePath) {
		Course course = CourseJSONUtils.loadCourseFromJson(filePath);
		return currentUser.alreadyHasCourse(course);
	}

	// Métodos relacionados a la búsqueda de usuarios en la ventana Comunidad

	public Set<User> getUsersStartingWith(String prefix) {
		Set<User> matchingUsers = userCatalog.getUsersStartingWith(prefix);
		matchingUsers.remove(currentUser);

		return matchingUsers;
	}

	/**
	 * Inicia sesión verificando las credenciales del usuario.
	 *
	 * @param username Nombre de usuario.
	 * @param password Contraseña.
	 * @return `true` si el inicio de sesión es exitoso, `false` si falla.
	 */
	public boolean login(String username, String password) {
		if (username.isEmpty() || password.isEmpty()) {
			return false;
		}

		User user = userCatalog.getUser(username);
		if (user == null || !user.getPassword().equals(password)) {
			return false;
		}

		this.currentUser = user;

		currentUser.startSession();
		currentUser.updateLoginStreak();

		return true;
	}

	// Métodos relacionados al tiempo de uso.

	public long getCurrentUsersActiveTimeInHours() {
		return currentUser.getActiveTimeInHours();
	}

	public long getCurrentUsersActiveTimeInSeconds() {
		return currentUser.getActiveTimeInSeconds();
	}

	public int getCurrentUsersCurrentLoginStreak() {
		return currentUser.getCurrentLoginStreak();
	}

	public int getCurrentUsersBestLoginStreak() {
		return currentUser.getBestLoginStreak();
	}

	public void endCurrentUserSession() {
		currentUser.endSession();
	}

	/**
	 * Registra un nuevo usuario en el sistema y lo autentica automáticamente.
	 *
	 * @param username Nombre de usuario.
	 * @param pfp      Imagen de perfil del usuario.
	 * @param email    Correo electrónico.
	 * @param password Contraseña.
	 * @return `true` si el registro es exitoso e inicia sesión, `false` si el
	 *         usuario ya existe.
	 */
	public boolean registerUser(String username, ImageIcon pfp, String email, String password) {
		if (userCatalog.getUser(username) != null) {
			return false;
		}

		User newUser = new User(username, password, email, pfp);
		this.currentUser = newUser;

		if (!userCatalog.existsUser(newUser)) {
			userCatalog.addUser(newUser);
			return login(newUser.getUsername(), newUser.getPassword()); // Auto-login después del registro
		}
		return false;
	}

	/**
	 * Actualiza la imagen de perfil del usuario actual.
	 *
	 * @param image Nueva imagen de perfil.
	 */
	public void setNewPfP(ImageIcon image) {
		this.currentUser.setProfilePic(image);
		userCatalog.updateUser(currentUser);
	}

	public void setAsAnswered(Course course, Question question) {
		currentUser.addAnsweredQuestion(course, question);
		if(isCompletedCourse(course)) {
			currentUser.addCompletedCourse(course);
		}
	}
	

	public int getProgress(Course course) {
		return currentUser.getCourseProgress(course);
	}

	public void updateCurrentUser() {
		userCatalog.updateUser(currentUser);
	}

	public User findUserByEmailAndUsername(String email, String username) {
		for (User user : userCatalog.getUsers()) {
			if (user.getEmail().equalsIgnoreCase(email) && user.getUsername().equalsIgnoreCase(username)) {
				return user;
			}
		}
		return null; // No encontrado
	}

	public void updateUserPassword(User user, String newPassword) {
		user.setPassword(newPassword);
		// Aquí puedes guardar los cambios en base de datos o en un archivo si es
		// necesario
	}

	public void logout() {
		this.currentUser = null;
	}
	
	public int getAnsweredQuestions(Course course) {
		return currentUser.getAnsweredQuestionsInCourse(course).size();
	}
	public boolean isAnsweredByActualUser(Course course, Question quest) {
		return this.currentUser.getAnsweredQuestionsInCourse(course).contains(quest.getId());
	}
	public void resetCourseProgress(Course course) {
		this.currentUser.resetCourseProgress(course);
		userCatalog.updateUser(currentUser);
	}
	/**
	 * Obtiene el nombre de un curso dado su ID.
	 *
	 * @param courseId ID del curso.
	 * @return Nombre del curso si existe, de lo contrario "Curso Desconocido".
	 */
	public String getCourseNameById(Long courseId) {
	    Course course = courseCatalog.getCourse(courseId);
	    return (course != null) ? course.getName() : "Curso Desconocido";
	}

}
