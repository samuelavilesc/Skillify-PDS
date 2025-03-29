package com.pds.skillify.ui.controller;


import com.pds.skillify.controller.Controller;
import com.pds.skillify.model.*;
import com.pds.skillify.ui.CourseExecutionWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CourseExecutionWindowController {

	private final CourseExecutionWindow window;
	private final Course course;
	private final List<Question> questions;

	private int currentQuestionIndex = 0;

	private ButtonGroup optionsGroup;
	private JRadioButton[] optionButtons;
	private JTextField userInputField;

	private static final Color GREEN_COLOR = new Color(0x80D855);
	private static final Color ERROR_COLOR = new Color(0xFF4C4C);
	private static final Color DEFAULT_TEXT_COLOR = Color.BLACK;

	public CourseExecutionWindowController(CourseExecutionWindow window, Course course) {
		this.window = window;
		this.course = course;
		this.questions = course.getQuestions();
	}

	public void mostrarPregunta() {
		while (currentQuestionIndex < questions.size()) {
			Question question = questions.get(currentQuestionIndex);

			if (Controller.getInstance().wasAnsweredByCurrentUser(course, question)) {
				currentQuestionIndex++;
				continue;
			}

			window.getLblQuestion().setText("<html><div style='text-align: center;'>" + question.getStatement() + "</div></html>");
			window.getResponsePanel().removeAll();

			if (question instanceof MultipleChoiceQuestion) {
				mostrarPreguntaMultipleChoice((MultipleChoiceQuestion) question);
			} else {
				mostrarPreguntaTexto();
			}

			window.getResponsePanel().revalidate();
			window.getResponsePanel().repaint();
			return;
		}

		SwingUtilities.invokeLater(() -> {
			JOptionPane.showMessageDialog(window, "Has completado todas las preguntas.", "Curso Finalizado", JOptionPane.INFORMATION_MESSAGE);
			window.dispose();
		});
	}


	private void mostrarPreguntaTexto() {
		userInputField = new JTextField();
		userInputField.setFont(new Font("Arial", Font.PLAIN, 14));
		userInputField.setPreferredSize(new Dimension(300, 30));
		userInputField.setHorizontalAlignment(JTextField.CENTER);
		userInputField.setBorder(BorderFactory.createLineBorder(GREEN_COLOR, 1));

		JPanel inputWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
		inputWrapper.setBackground(Color.WHITE);
		inputWrapper.add(userInputField);
		window.getResponsePanel().add(inputWrapper);
	}

	private void mostrarPreguntaMultipleChoice(MultipleChoiceQuestion question) {
		optionsGroup = new ButtonGroup();
		optionButtons = new JRadioButton[question.getOptions().size()];

		JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
		optionsPanel.setOpaque(false);
		optionsPanel.setBackground(Color.WHITE);

		for (int i = 0; i < question.getOptions().size(); i++) {
			JRadioButton option = new JRadioButton(question.getOptions().get(i));
			option.setFont(new Font("Arial", Font.PLAIN, 14));
			option.setActionCommand(String.valueOf(i));
			option.setBackground(Color.WHITE);
			option.setForeground(DEFAULT_TEXT_COLOR);
			optionsGroup.add(option);
			optionButtons[i] = option;
			optionsPanel.add(option);
		}

		JScrollPane scrollPane = new JScrollPane(optionsPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEmptyBorder()); 
		scrollPane.setPreferredSize(new Dimension(480, 60)); 

		window.getResponsePanel().add(scrollPane);
	}

	public void procesarRespuesta() {
		Question question = questions.get(currentQuestionIndex);
		boolean esCorrecto = false;
		Controller.getInstance().setQuestionAsAnswered(course, question);

		if (question instanceof MultipleChoiceQuestion) {
			MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;
			int respuestaCorrectaIndex = mcq.getCorrectAnswer();

			if (optionsGroup.getSelection() != null) {
				int respuestaUsuarioIndex = Integer.parseInt(optionsGroup.getSelection().getActionCommand());
				esCorrecto = mcq.checkAnswer(String.valueOf(respuestaUsuarioIndex));

				for (JRadioButton option : optionButtons) {
					option.setForeground(DEFAULT_TEXT_COLOR);
					option.setFont(new Font("Arial", Font.PLAIN, 14));
				}

				if (!esCorrecto) {
					optionButtons[respuestaUsuarioIndex].setForeground(ERROR_COLOR);

					if (course.getMode() == CourseMode.REPETITION) {
						JOptionPane.showMessageDialog(window, "Respuesta incorrecta. Inténtalo de nuevo.", "Incorrecto", JOptionPane.ERROR_MESSAGE);
						return;
					}

					optionButtons[respuestaCorrectaIndex].setForeground(GREEN_COLOR);
					optionButtons[respuestaCorrectaIndex].setFont(new Font("Arial", Font.BOLD, 14));
				} else {
					optionButtons[respuestaUsuarioIndex].setForeground(GREEN_COLOR);
					optionButtons[respuestaUsuarioIndex].setFont(new Font("Arial", Font.BOLD, 14));
				}

				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						currentQuestionIndex++;
						SwingUtilities.invokeLater(() -> mostrarPregunta());
					}
				}, 3000);
			}
		} else {
			String respuestaUsuario = userInputField.getText().trim();
			esCorrecto = question.checkAnswer(respuestaUsuario);

			if (esCorrecto) {
				JOptionPane.showMessageDialog(window, "¡Respuesta correcta!", "Correcto", JOptionPane.INFORMATION_MESSAGE);
				currentQuestionIndex++;
				mostrarPregunta();
			} else {
				if (course.getMode() == CourseMode.REPETITION) {
					JOptionPane.showMessageDialog(window, "Respuesta incorrecta. Inténtalo de nuevo.", "Incorrecto", JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(window, "Respuesta incorrecta.", "Incorrecto", JOptionPane.ERROR_MESSAGE);
				currentQuestionIndex++;
				mostrarPregunta();
			}
		}
	}

	public Course getCourse() {
		return course;
	}

	public Color getGreenColor() {
		return GREEN_COLOR;
	}
}
