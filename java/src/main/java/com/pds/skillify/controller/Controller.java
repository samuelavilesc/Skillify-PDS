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

	private Controller() {
		initializeCatalogs();
	}

	private void initializeCatalogs() {
		this.userCatalog = UserCatalog.getInstance();
		this.courseCatalog = CourseCatalog.getInstance();

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

	// ---------------------- Gestión de Usuarios ----------------------

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

	/**
	 * Cierra la sesión del usuario actual.
	 */
	public void logout() {
		this.currentUser = null;
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
		  if ((userCatalog.getUser(username) != null) || userCatalog.emailExists(email)) {
			return false;
		}

		User newUser = new User(username, password, email, pfp);
		this.currentUser = newUser;

		if (!userCatalog.existsUser(newUser)) {
			userCatalog.addUser(newUser);
			return login(newUser.getUsername(), newUser.getPassword());
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

	/**
	 * Busca un usuario por email y nombre de usuario.
	 *
	 * @param email    Correo del usuario.
	 * @param username Nombre de usuario.
	 * @return Usuario si se encuentra, `null` en caso contrario.
	 */
	public User getUserByEmailAndUsername(String email, String username) {
		for (User user : userCatalog.getUsers()) {
			if (user.getEmail().equalsIgnoreCase(email) && user.getUsername().equalsIgnoreCase(username)) {
				return user;
			}
		}

		return null;
	}

	/**
	 * Actualiza la contraseña de un usuario.
	 *
	 * @param user        Usuario al que se le actualizará la contraseña.
	 * @param newPassword Nueva contraseña.
	 */
	public void updateUserPassword(User user, String newPassword) {
		user.setPassword(newPassword);
	}

	/**
	 * Obtiene el usuario actual.
	 *
	 * @return Usuario actualmente autenticado.
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Establece un usuario como el usuario actual.
	 *
	 * @param user Usuario a establecer como actual.
	 */
	public void setCurrentUser(User user) {
		this.currentUser = user;
	}

	/**
	 * Actualiza la información del usuario actual en el catálogo de usuarios.
	 */
	public void updateCurrentUser() {
		userCatalog.updateUser(currentUser);
	}

	// ---------------------- Gestión de Cursos ----------------------

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

		
		Course existingCourse = courseCatalog.getCourse(newCourse.getName());

		if (existingCourse == null) {
			
			courseCatalog.addCourse(newCourse);
			existingCourse = newCourse;
		}

		

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
	 * Reinicia el progreso del usuario en un curso.
	 *
	 * @param course Curso cuyo progreso se reiniciará.
	 */
	public void resetCurrentUsersCourseProgress(Course course) {
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
	 * Marca una pregunta como respondida por el usuario en un curso.
	 *
	 * @param course   Curso donde se responde la pregunta.
	 * @param question Pregunta respondida.
	 */
	public void setQuestionAsAnswered(Course course, Question question) {
		currentUser.addAnsweredQuestion(course, question);
		if (isCourseCompleted(course)) {
			currentUser.addCompletedCourse(course);
		}
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

	/**
	 * Verifica si el usuario actual ha completado todas las preguntas del curso especificado.
	 *
	 * @param course curso a verificar
	 * @return true si el curso está completado, false en caso contrario
	 */
	private boolean isCourseCompleted(Course course) {
		return getCurrentUsersAnsweredQuestionsInCourse(course) == course.getQuestions().size();
	}

	/**
	 * Obtiene la cantidad de preguntas respondidas por el usuario actual en un curso determinado.
	 *
	 * @param course curso del cual se contarán las preguntas respondidas
	 * @return número de preguntas respondidas por el usuario actual
	 */
	public int getCurrentUsersAnsweredQuestionsInCourse(Course course) {
		return currentUser.getAnsweredQuestionsInCourse(course).size();
	}

	/**
	 * Verifica si una pregunta específica ha sido respondida por el usuario actual en el curso dado.
	 *
	 * @param course curso que contiene la pregunta
	 * @param quest pregunta a verificar
	 * @return true si la pregunta ha sido respondida por el usuario actual, false en caso contrario
	 */
	public boolean wasAnsweredByCurrentUser(Course course, Question quest) {
		return this.currentUser.getAnsweredQuestionsInCourse(course).contains(quest.getId());
	}

	// ---------------------- Métodos Auxiliares ----------------------

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

	/**
	 * Devuelve un conjunto de usuarios cuyos nombres empiezan con el prefijo especificado,
	 * excluyendo al usuario actual.
	 *
	 * @param prefix prefijo con el que comienzan los nombres de los usuarios buscados
	 * @return conjunto de usuarios cuyos nombres comienzan con el prefijo dado, excluyendo al usuario actual
	 */
	public Set<User> getUsersStartingWith(String prefix) {
		Set<User> matchingUsers = userCatalog.getUsersStartingWith(prefix);
		matchingUsers.remove(currentUser);

		return matchingUsers;
	}
	// ---------------------- Tiempo de Uso ----------------------

	/**
	 * Devuelve el tiempo activo total del usuario actual en horas.
	 * @return tiempo activo en horas del usuario actual
	 */
	public long getCurrentUsersActiveTimeInHours() {
		return currentUser.getActiveTimeInHours();
	}

	/**
	 * Devuelve el tiempo activo total del usuario actual en segundos.
	 * @return tiempo activo en segundos del usuario actual
	 */
	public long getCurrentUsersActiveTimeInSeconds() {
		return currentUser.getActiveTimeInSeconds();
	}

	/**
	 * Devuelve la racha actual de inicio de sesión consecutivos del usuario actual.
	 * @return número actual de días consecutivos iniciando sesión
	 */
	public int getCurrentUsersCurrentLoginStreak() {
		return currentUser.getCurrentLoginStreak();
	}

	/**
	 * Devuelve la mejor racha de inicio de sesión consecutivos alcanzada por el usuario actual.
	 * @return mejor número de días consecutivos iniciando sesión
	 */
	public int getCurrentUsersBestLoginStreak() {
		return currentUser.getBestLoginStreak();
	}

	/**
	 * Finaliza la sesión actual del usuario.
	 */
	public void endCurrentUserSession() {
		currentUser.endSession();
	}
	

}
