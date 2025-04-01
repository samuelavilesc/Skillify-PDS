package com.pds.skillify.ui;

import com.pds.skillify.model.Course;
import com.pds.skillify.ui.controller.*;

import javax.swing.*;
import java.awt.*;

public class CourseExecutionWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel mainPanel;
	private JLabel lblQuestion;
	private JPanel responsePanel;
	private JButton answerButton;

	private final CourseExecutionWindowController controller;

	public CourseExecutionWindow(Course course) {
		this.controller = new CourseExecutionWindowController(this, course);
		initializeUI();
		controller.mostrarPregunta();
		setVisible(true);
	}

	private void initializeUI() {
		setTitle(controller.getCourse().getName());
		setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
		setSize(500, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		mainPanel = new JPanel(new BorderLayout(10, 5));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		mainPanel.setBackground(Color.WHITE);

		lblQuestion = new JLabel("", SwingConstants.CENTER);
		lblQuestion.setFont(new Font("Arial", Font.BOLD, 16));
		lblQuestion.setForeground(controller.getGreenColor());
		lblQuestion.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		mainPanel.add(lblQuestion, BorderLayout.NORTH);

		responsePanel = new JPanel();
		responsePanel.setLayout(new BoxLayout(responsePanel, BoxLayout.Y_AXIS));
		responsePanel.setBackground(Color.WHITE);
		mainPanel.add(responsePanel, BorderLayout.CENTER);

		answerButton = new JButton("Responder");
		answerButton.setFont(new Font("Arial", Font.BOLD, 14));
		answerButton.setPreferredSize(new Dimension(180, 35));
		answerButton.setForeground(Color.WHITE);
		answerButton.setBackground(controller.getGreenColor());
		answerButton.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		answerButton.setFocusPainted(false);
		answerButton.addActionListener(e -> controller.procesarRespuesta());

		JPanel responseContainer = new JPanel(new BorderLayout(5, 5));
		responseContainer.setBackground(Color.WHITE);
		responseContainer.add(responsePanel, BorderLayout.NORTH);
		responseContainer.add(answerButton, BorderLayout.SOUTH);

		mainPanel.add(responseContainer, BorderLayout.SOUTH);
		add(mainPanel);
	}

	
	public JLabel getLblQuestion() {
		return lblQuestion;
	}

	public JPanel getResponsePanel() {
		return responsePanel;
	}

	public JButton getAnswerButton() {
		return answerButton;
	}
}
