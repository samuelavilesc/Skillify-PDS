package com.pds.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.ImageIcon;
import com.pds.skillify.model.*;
import com.pds.skillify.utils.CourseJSONUtils;

public class Controller {
	// Catálogos que gestionan usuarios, cursos y preguntas
	private UserCatalog userCatalog;
	private CourseCatalog courseCatalog;

	// Instancia única del controlador (Singleton).
	private static Controller uniqueInstance = null;

	// Usuario actualmente autenticado en el sistema.
	private User actualUser;

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
		for (Course course : actualUser.getAllCourses()) {
			if (actualUser.getCourseProgress(course) == 100) {
				finishedCourses.add(course);
			}
		}
		return finishedCourses;
	}

	public User getActualUser() {
		return actualUser;
	}

	public void setActualUser(User actualUser) {
		this.actualUser = actualUser;
	}

	/**
	 * Añade un curso al usuario actual cargándolo desde un archivo JSON.
	 *
	 * @param filePath Ruta del archivo JSON con el curso.
	 */
	public void addCourseToCurrentUser(String filePath) {
		Course newCourse = CourseJSONUtils.loadCourseFromJson(filePath);

		if (actualUser == null) {
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
		if (!actualUser.alreadyHasCourse(existingCourse)) {
			actualUser.addCourse(existingCourse);
			userCatalog.updateUser(actualUser);
		}
	}

	/**
	 * Obtiene todos los cursos del usuario actual.
	 *
	 * @return Un conjunto con los cursos del usuario actual.
	 */
	public Set<Course> getAllCurrentUserCourses() {
		return actualUser.getAllCourses();
	}

	/**
	 * Obtiene el progreso del usuario actual en un curso específico.
	 *
	 * @param course Curso a consultar.
	 * @return Progreso del usuario en el curso (0-100).
	 */
	public int getCurrentUsersProgressInCourse(Course course) {
		return actualUser.getCourseProgress(course);
	}

	/**
	 * Verifica si el usuario actual ya tiene un curso específico.
	 *
	 * @param filePath Ruta del archivo JSON con el curso.
	 * @return `true` si el usuario ya tiene el curso, `false` en caso contrario.
	 */
	public boolean currentUserAlreadyHasCourse(String filePath) {
		Course course = CourseJSONUtils.loadCourseFromJson(filePath);
		return actualUser.alreadyHasCourse(course);
	}
	
	// Métodos relacionados a la búsqueda de usuarios en la ventana Comunidad
	
	public Set<User> getUsersStartingWith(String prefix){
		Set<User> matchingUsers = userCatalog.getUsersStartingWith(prefix);
		matchingUsers.remove(actualUser);
		
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

		this.actualUser = user;

		actualUser.startSession();
		actualUser.updateLoginStreak();

		return true;
	}

	// Métodos relacionados al tiempo de uso.

	public long getCurrentUsersActiveTimeInHours() {
		return actualUser.getActiveTimeInHours();
	}
	
	public long getCurrentUsersActiveTimeInSeconds() {
		return actualUser.getActiveTimeInSeconds();
	}

	public int getCurrentUsersCurrentLoginStreak() {
		return actualUser.getCurrentLoginStreak();
	}

	public int getCurrentUsersBestLoginStreak() {
		return actualUser.getBestLoginStreak();
	}

	public void endCurrentUserSession() {
		actualUser.endSession();
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
		this.actualUser = newUser;

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
		this.actualUser.setProfilePic(image);
		userCatalog.updateUser(actualUser);
	}

	public void setAsAnswered(Course course, Question question) {
		actualUser.addAnsweredQuestion(course, question);
	}

	public int getProgress(Course course) {
		return actualUser.getCourseProgress(course);
	}

	public void updateCurrentUser() {
		userCatalog.updateUser(actualUser);
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
	        // Aquí puedes guardar los cambios en base de datos o en un archivo si es necesario
	    }
}
