package com.pds.skillify.ui;

import javax.swing.*;

import com.pds.controller.Controller;
import com.pds.skillify.model.Course;
import com.pds.skillify.ui.controller.CourseStartWindowController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class CourseStartWindow extends JFrame {

	private static final int WIDTH = 400;
	private static final int HEIGHT = 500;

	private Course course;
	private int answeredQuestions;
	private JComboBox<String> modeSelector;
	private JButton startButton, resetButton;

	private JLabel answeredQuestionsLabel;

	

	public CourseStartWindow(Course cours) {
		this.course = cours;
		this.answeredQuestions = Controller.getInstance().getAnsweredQuestions(cours);

		initialize();
		new CourseStartWindowController(this);
		setVisible(true);
	}

	private void initialize() {
		setTitle("Iniciar Curso - " + course.getName());
		setIconImage(new ImageIcon(getClass().getResource("/curso.png")).getImage());
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new BorderLayout());

		// Panel superior con imagen
		JPanel topPanel = new JPanel(new BorderLayout());
		ImageIcon logo = new ImageIcon(getClass().getResource("/curso.png"));
		Image img = logo.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		JLabel logoLabel = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
		topPanel.add(logoLabel, BorderLayout.CENTER);
		add(topPanel, BorderLayout.NORTH);

		// Panel central con la información
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		// Título del curso centrado
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel titleLabel = new JLabel(course.getName());
		titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
		titlePanel.add(titleLabel);
		centerPanel.add(titlePanel);
		centerPanel.add(Box.createVerticalStrut(10));

		// Descripción del curso
		JTextArea descriptionArea = new JTextArea(course.getDescription());
		descriptionArea.setWrapStyleWord(true);
		descriptionArea.setLineWrap(true);
		descriptionArea.setEditable(false);
		descriptionArea.setBorder(BorderFactory.createTitledBorder("Descripción"));
		centerPanel.add(descriptionArea);
		centerPanel.add(Box.createVerticalStrut(10));

		// Panel para total de preguntas y respondidas
		JPanel questionsPanel = new JPanel();
		questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));

		JLabel totalQuestionsLabel = new JLabel("Total de preguntas: " + course.getQuestions().size(),
				SwingConstants.CENTER);
		answeredQuestionsLabel = new JLabel("Preguntas respondidas: " + answeredQuestions, SwingConstants.CENTER);

		totalQuestionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		answeredQuestionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		questionsPanel.add(totalQuestionsLabel);
		questionsPanel.add(answeredQuestionsLabel);

		centerPanel.add(questionsPanel);
		centerPanel.add(Box.createVerticalStrut(10));

		// Selector de modo
		modeSelector = new JComboBox<>(new String[] { "Aleatorio", "Secuencial", "Repetición" });
		modeSelector.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(modeSelector);
		centerPanel.add(Box.createVerticalStrut(10));

		add(centerPanel, BorderLayout.CENTER);

		// Panel inferior con botones
		JPanel bottomPanel = new JPanel();
		startButton = new JButton("Empezar");
		startButton.setPreferredSize(new Dimension(120, 30));

		resetButton = new JButton("Resetear Curso");
		resetButton.setPreferredSize(new Dimension(120, 30));
		resetButton.setBackground(Color.RED);
		resetButton.setForeground(Color.WHITE);

		bottomPanel.add(startButton);
		bottomPanel.add(resetButton);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	public Course getCourse() {
		return course;
	}

	public JButton getResetButton() {
		return resetButton;
	}

	public void setResetButton(JButton resetButton) {
		this.resetButton = resetButton;
	}

	public JComboBox<String> getModeSelector() {
		return modeSelector;
	}

	public JButton getStartButton() {
		return startButton;
	}
	public JLabel getAnsweredQuestionsLabel() {
		return answeredQuestionsLabel;
	}

	public void setAnsweredQuestionsLabel(JLabel answeredQuestionsLabel) {
		this.answeredQuestionsLabel = answeredQuestionsLabel;
	}
}
