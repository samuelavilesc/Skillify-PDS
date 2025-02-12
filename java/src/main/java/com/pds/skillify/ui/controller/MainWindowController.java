package com.pds.skillify.ui.controller;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.pds.controller.Controller;
import com.pds.skillify.model.Course;
import com.pds.skillify.ui.AchievementsWindow;
import com.pds.skillify.ui.CommunityWindow;
import com.pds.skillify.ui.ConfigureUserWindow;
import com.pds.skillify.ui.CourseStartWindow;
import com.pds.skillify.ui.LoginWindow;
import com.pds.skillify.ui.MainWindow;
import com.pds.skillify.utils.CourseJSONUtils;

public class MainWindowController {

	private MainWindow view;

	public MainWindowController(MainWindow view) {
		this.view = view;
		initializeControllers();
	}

	/**
	 * Inicializa los controladores de eventos.
	 */
	private void initializeControllers() {
		handleClickOnSettings(view.getSettingsButton());
		handleClickOnProfile(view.getProfileButton());
		handleClickOnImportCourse(view.getImportButton());
		handleClickOnCourse(view.getCourseList());
		handleClickOnCommunity(view.getCommunityButton());
		handleClosingWindow();
		handleClickOnLogout(view.getLogoutButton());
	}

	/**
	 * Maneja el clic en el botón de configuración.
	 */
	private void handleClickOnSettings(JButton settingsButton) {
		settingsButton.addActionListener(e -> openUserConfiguration());
	}

	/**
	 * Maneja el clic en el botón de perfil para abrir la ventana de logros.
	 */

	private void handleClickOnProfile(JButton profileButton) {
		profileButton.addActionListener(e -> {
			List<String> courses = Arrays.asList("Introducción a la Programación", "Java desde Cero",
					"Python para Principiantes", "Desarrollo Web con HTML y CSS", "JavaScript Interactivo",
					"SQL y Bases de Datos", "Estructuras de Datos y Algoritmos", "Programación Orientada a Objetos",
					"Desarrollo de APIs con Spring Boot");

			// List<String> completedCourses =
			// Controller.getInstance().getCompletedCourses();
			new AchievementsWindow(Controller.getInstance().getActualUser()); // Abre la ventana de logros con la lista de cursos
			view.dispose();
		});
	}

	/**
	 * Maneja el clic en el botón de importar curso.
	 */
	private void handleClickOnImportCourse(JButton importButton) {
		importButton.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Seleccionar Curso");
			fileChooser.setFileFilter(
					new javax.swing.filechooser.FileNameExtensionFilter("Archivos JSON (*.json)", "json"));

			int seleccion = fileChooser.showOpenDialog(null);
			if (seleccion == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();

				try {
					if (!CourseJSONUtils.validateQuizJSON(selectedFile.getAbsolutePath())) {
						JOptionPane.showMessageDialog(view, "El archivo seleccionado no tiene el formato correcto.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				if (Controller.getInstance().currentUserAlreadyHasCourse(selectedFile.getAbsolutePath())) {
					JOptionPane.showMessageDialog(view, "Ya has importado ese curso anteriormente.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				Controller.getInstance().addCourseToCurrentUser(selectedFile.getAbsolutePath());
				view.updateCoursesList();
			}
		});
	}

	/**
	 * Maneja la selección de un curso en la lista.
	 */
	private void handleClickOnCourse(JList<Course> courseList) {
		courseList.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				Course selectedCourse = courseList.getSelectedValue();
				if (selectedCourse != null) {
					new CourseStartWindow(selectedCourse);
				}
			}
		});
	}
	
	private void handleClickOnCommunity(JButton communityButon) {
		communityButon.addActionListener(e -> openCommunityWindow());
	}
	private void handleClickOnLogout(JButton logoutButton) {
		logoutButton.addActionListener(e -> logout());
	}
	private void handleClosingWindow() {
		view.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Ejecutar en el hilo de eventos de Swing para evitar bloqueos
				SwingUtilities.invokeLater(() -> {
					Controller.getInstance().endCurrentUserSession();
					Controller.getInstance().updateCurrentUser();
				});
			}
		});
	}

	/**
	 * Abre la ventana de configuración del usuario.
	 */
	private void openUserConfiguration() {
		new ConfigureUserWindow();
		view.dispose(); // Cierra la ventana principal
	}
	
	private void openCommunityWindow() {
		new CommunityWindow();
		view.dispose();
	}

	private void logout() {
		Controller.getInstance().endCurrentUserSession();
		Controller.getInstance().updateCurrentUser();
		Controller.getInstance().logout();
		new LoginWindow();
		view.dispose();
	}

	

}
