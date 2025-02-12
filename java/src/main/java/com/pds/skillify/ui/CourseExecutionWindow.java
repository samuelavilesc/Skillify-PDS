package com.pds.skillify.ui;

import com.pds.skillify.model.*;
import com.pds.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CourseExecutionWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final Color GREEN_COLOR = new Color(0x80D855);
    private static final Color ERROR_COLOR = new Color(0xFF4C4C);
    private static final Color DEFAULT_TEXT_COLOR = Color.BLACK;

    private JLabel lblQuestion;
    private JPanel responsePanel, mainPanel;
    private JButton answerButton;
    private JTextField userInputField;
    private ButtonGroup optionsGroup;
    private JRadioButton[] optionButtons;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private Course course;

    public CourseExecutionWindow(Course course) {
        this.questions = course.getQuestions();
        this.course = course;

        setTitle(course.getName());
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
        lblQuestion.setForeground(GREEN_COLOR);
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
        answerButton.setBackground(GREEN_COLOR);
        answerButton.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        answerButton.setFocusPainted(false);
        answerButton.addActionListener(this::procesarRespuesta);

        JPanel responseContainer = new JPanel(new BorderLayout(5, 5));
        responseContainer.setBackground(Color.WHITE);
        responseContainer.add(responsePanel, BorderLayout.NORTH);
        responseContainer.add(answerButton, BorderLayout.SOUTH);

        mainPanel.add(responseContainer, BorderLayout.SOUTH);
        add(mainPanel);

        mostrarPregunta();
        setVisible(true);
    }

    private void mostrarPregunta() {
        while (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);

            // **Verificar si la pregunta ya ha sido respondida por el usuario**
            if (Controller.getInstance().isAnsweredByActualUser(course, question)) {
                currentQuestionIndex++; // **Saltar a la siguiente pregunta**
                continue;
            }

            lblQuestion.setText("<html><div style='text-align: center;'>" + question.getStatement() + "</div></html>");
            responsePanel.removeAll();

            if (question instanceof MultipleChoiceQuestion) {
                mostrarPreguntaMultipleChoice((MultipleChoiceQuestion) question);
            } else {
                mostrarPreguntaTexto();
            }

            responsePanel.revalidate();
            responsePanel.repaint();
            return; // **Salir del bucle después de encontrar una pregunta sin responder**
        }

        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "Has completado todas las preguntas.", "Curso Finalizado", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Cierra la ventana actual
        });
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

        responsePanel.add(optionsPanel);
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
        responsePanel.add(inputWrapper);
    }

    private void procesarRespuesta(ActionEvent e) {
        Question question = questions.get(currentQuestionIndex);
        boolean esCorrecto = false;
        Controller.getInstance().setAsAnswered(course, question);

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
                        JOptionPane.showMessageDialog(this, "Respuesta incorrecta. Inténtalo de nuevo.",
                            "Incorrecto", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "¡Respuesta correcta!", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                currentQuestionIndex++;
                mostrarPregunta();
            } else {
                if (course.getMode() == CourseMode.REPETITION) {
                    JOptionPane.showMessageDialog(this, "Respuesta incorrecta. Inténtalo de nuevo.",
                        "Incorrecto", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(this, "Respuesta incorrecta.", "Incorrecto", JOptionPane.ERROR_MESSAGE);
                currentQuestionIndex++;
                mostrarPregunta();
            }
        }
    }
}
