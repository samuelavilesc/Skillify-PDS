package com.pds.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;

import com.pds.skillify.model.Course;
import com.pds.skillify.model.User;
import com.pds.skillify.model.UserCatalog;
import com.pds.skillify.utils.CourseJSONUtils;

public class Controller {
	// Catálogo de usuarios que almacena y gestiona todos los usuarios del sistema.
	private UserCatalog catalog;

	// Instancia única del controlador (Singleton).
	private static Controller uniqueInstance = null;
	// Usuario actualmente autenticado en el sistema.
	private User actualUser;

	/**
	 * Constructor privado del controlador. Inicializa las estrategias de descuento,
	 * los adaptadores para la capa de persistencia y los catálogos.
	 * 
	 * Este método es llamado solo una vez debido al patrón Singleton.
	 */
	private Controller() {
		intializeCatalog();
	}

	/**
	 * Obtiene la instancia única del controlador (Singleton).
	 * 
	 * @return Instancia única de la clase `Controller`.
	 */

	public static Controller getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Controller();
		}
		return uniqueInstance;
	}

	/**
	 * Inicializa el catálogo de usuarios cargándolos desde la base de datos.
	 */
	private void intializeCatalog() {
		this.catalog = UserCatalog.getInstance();
	}

	public List<Course> getFinishedCourses() {
		// TODO
		return new ArrayList<>();
	}

	public User getActualUser() {
		return actualUser;
	}

	public void setActualUser(User actualUser) {
		this.actualUser = actualUser;
	}

	public void addCourseToCurrentUser(String filePath) {
		Course newCourse = CourseJSONUtils.loadCourseFromJson(filePath);
		actualUser.addCourse(newCourse);
	}

	public Set<Course> getAllCurrentUserCourses() {
		return actualUser.getAllCourses();
	}

	public int getCurrentUsersProgressInCourse(Course course) {
		return actualUser.getCourseProgress(course);
	}
	
	public boolean currentUserAlreadyHasCourse(String filePath) {
		Course course = CourseJSONUtils.loadCourseFromJson(filePath);
		return actualUser.alreadyHasCourse(course);
	}

	public boolean login(String username, String password) {
		// si alguno de los campos esta vacio, no puede iniciar sesion
		if (username.isEmpty() || password.isEmpty()) {
			return false;
		}
		// si estan los dos
		User user = catalog.getUser(username);

		if (user == null)
			return false;
		if (user.getPassword().equals(password)) {
			this.actualUser = user;
		}
		return true;

	}

	public boolean registerUser(String username, ImageIcon pfp, String email, String password) {
		User usuarioExistente = catalog.getUser(username);
		if (usuarioExistente != null) {
			return false;
		}

		User newUser = new User(username, password, email, pfp);
		this.actualUser = newUser;
		// si no esta registrado, lo añadimos al catalogo de usuarios
		if (!catalog.existsUser(newUser)) {
			catalog.addUser(newUser);
			// adaptadorUsuario.registrarUsuario(newUser);

			return login(newUser.getUsername(), newUser.getPassword()); // autonicio de sesión para que se redirija
																		// a la main window
		}
		return false;

	}

	public void setNewPfP(ImageIcon image) {
		this.actualUser.setProfilePic(image);
	}
}
