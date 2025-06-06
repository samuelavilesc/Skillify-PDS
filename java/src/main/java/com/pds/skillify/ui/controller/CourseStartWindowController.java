package com.pds.skillify.ui.controller;

import com.pds.skillify.ui.CourseStartWindow;
import com.pds.skillify.controller.Controller;
import com.pds.skillify.model.Course;
import com.pds.skillify.model.CourseMode;
import com.pds.skillify.ui.CourseExecutionWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;
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
				assignCourseMode(course);
				openCourseExecution(course);
				view.dispose();
			}
		});
		view.getResetButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetCourseProgress(view.getCourse());
			}
		});
	}

	/**
	 * Abre la ventana de ejecución del curso y actualiza el usuario al cerrarse.
	 */
	private void openCourseExecution(Course course) {
		CourseExecutionWindow window = new CourseExecutionWindow(course);

	
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				
				SwingUtilities.invokeLater(() -> {
					Controller.getInstance().updateCurrentUser();
				});
			}
		});

	}

	/**
	 * Asigna el modo de aprendizaje seleccionado al curso proporcionado.
	 * El modo seleccionado puede ser aleatorio, secuencial o repetición, y determina
	 * la forma en que se presentarán las preguntas al usuario durante el curso.
	 *
	 * @param course Objeto Course al que se asignará el modo seleccionado.
	 */
	private void assignCourseMode(Course course) {
		String selectedMode = (String) view.getModeSelector().getSelectedItem();

		if ("Aleatorio".equals(selectedMode)) {
			course.setMode(CourseMode.RANDOM);
		} else if ("Secuencial".equals(selectedMode)) {
			course.setMode(CourseMode.SEQUENTIAL);
		} else if ("Repetición".equals(selectedMode)) {
			course.setMode(CourseMode.REPETITION);
		}
	}

	/**
	 * Resetea el progreso del usuario en el curso y actualiza la interfaz.
	 */
	private void resetCourseProgress(Course course) {
		int confirm = JOptionPane.showConfirmDialog(view,
				"¿Estás seguro de que quieres resetear el progreso en este curso?", "Confirmar Reset",
				JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			Controller.getInstance().resetCurrentUsersCourseProgress(course);
			int answeredQuestions = 0;
			view.getAnsweredQuestionsLabel().setText("Preguntas respondidas: " + answeredQuestions);
			JOptionPane.showMessageDialog(view, "El progreso del curso ha sido reseteado.", "Progreso Resetado",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
