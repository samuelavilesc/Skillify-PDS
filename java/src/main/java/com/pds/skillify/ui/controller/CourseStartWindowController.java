package com.pds.skillify.ui.controller;

import com.pds.skillify.ui.CourseStartWindow;
import com.pds.controller.Controller;
import com.pds.skillify.model.Course;
import com.pds.skillify.ui.CourseExecutionWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

public class CourseStartWindowController {

    private CourseStartWindow view;

    public CourseStartWindowController(CourseStartWindow view) {
        this.view = view;
        addListeners();
    }

    private void addListeners() {
        view.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Course course = view.getCourse();
                openCourseExecution(course);
                view.dispose();
            }
        });
    }
    /**
	 * Abre la ventana de ejecución del curso y actualiza el usuario al cerrarse.
	 */
	private void openCourseExecution(Course course) {
		CourseExecutionWindow window = new CourseExecutionWindow(course);

		// Agrega un WindowListener para detectar el cierre de la ventana
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// Ejecutar en el hilo de eventos de Swing para evitar bloqueos
				SwingUtilities.invokeLater(() -> {
					Controller.getInstance().updateCurrentUser();
				});
			}
		});
	}
}
