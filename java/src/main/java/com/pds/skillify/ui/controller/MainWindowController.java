package com.pds.skillify.ui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;

import com.pds.controller.Controller;
import com.pds.skillify.model.Course;
import com.pds.skillify.ui.AchievementsWindow;
import com.pds.skillify.ui.ConfigureUserWindow;
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
	}

	/**
	 * Maneja el clic en el botón de configuración.
	 */
	private void handleClickOnSettings(JButton settingsButton) {
		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openUserConfiguration();
			}
		});
	}

	/**
	 * Maneja el clic en el botón de perfil (puede abrir el perfil del usuario en el
	 * futuro).
	 */
	private void handleClickOnProfile(JButton profileButton) {
        profileButton.addActionListener(e -> {
            List<String> courses = Arrays.asList(
                "Introducción a la Programación",
                "Java desde Cero",
                "Python para Principiantes",
                "Desarrollo Web con HTML y CSS",
                "JavaScript Interactivo",
                "SQL y Bases de Datos",
                "Estructuras de Datos y Algoritmos",
                "Programación Orientada a Objetos",
                "Desarrollo de APIs con Spring Boot"
            );
            
            // List<String> completedCourses = Controller.getInstance().getCompletedCourses();
            new AchievementsWindow(courses); // Abre la ventana de logros con la lista de cursos
        });
    }

	/**
	 * Maneja el clic en el botón de importar curso.
	 */
	private void handleClickOnImportCourse(JButton importButton) {
		importButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

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
					openCourseDetails(selectedCourse);
				}
			}
		});
	}

	/**
	 * Abre la ventana de configuración del usuario y cierra la ventana principal.
	 */
	private void openUserConfiguration() {

		ConfigureUserWindow configWindow = new ConfigureUserWindow();
		configWindow.setVisible(true); // Asegurar que la ventana se muestra correctamente

		view.dispose(); // Cierra la ventana principal
	}

	/**
	 * Abre los detalles del curso seleccionado.
	 */
	private void openCourseDetails(Course course) {
		// TODO: Implementar la apertura de la ventana de detalles del curso
		System.out.println("Abriendo detalles del curso: " + course.getName());
	}
}
