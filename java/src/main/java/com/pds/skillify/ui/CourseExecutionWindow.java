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

    /**
	 * 
	 */
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
        this.course=course;

        setTitle("Skillify");
        setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
        setSize(500, 180);
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
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            lblQuestion.setText("<html><div style='text-align: center;'>" + question.getStatement() + "</div></html>");

            responsePanel.removeAll();

            if (question instanceof MultipleChoiceQuestion) {
                mostrarPreguntaMultipleChoice((MultipleChoiceQuestion) question);
            } else {
                mostrarPreguntaTexto(); // Para rellenar o para ordenar
            }

            responsePanel.revalidate();
            responsePanel.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Has completado todas las preguntas.", "Curso Finalizado", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    private void mostrarPreguntaMultipleChoice(MultipleChoiceQuestion question) {
        optionsGroup = new ButtonGroup();
        optionButtons = new JRadioButton[question.getOptions().size()];

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(1, 0, 10, 5)); // Distribuye en una fila horizontal
        optionsPanel.setOpaque(true);

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

        // *Agregar scroll horizontal si las respuestas son muy largas*
        JScrollPane scrollPane = new JScrollPane(optionsPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(400, 50)); // Ajusta la altura del scroll

        responsePanel.add(scrollPane);
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
        int respuestaCorrectaIndex = -1;
        Controller.getInstance().setAsAnswered(course, question);

        if (question instanceof MultipleChoiceQuestion) {
            MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;
            respuestaCorrectaIndex = mcq.getCorrectAnswer();

            if (optionsGroup.getSelection() != null) {
                int respuestaUsuarioIndex = Integer.parseInt(optionsGroup.getSelection().getActionCommand());
                esCorrecto = mcq.checkAnswer(String.valueOf(respuestaUsuarioIndex));

                // Restaurar los colores por defecto antes de resaltar
                for (JRadioButton option : optionButtons) {
                    option.setForeground(DEFAULT_TEXT_COLOR);
                    option.setFont(new Font("Arial", Font.PLAIN, 14));
                }

                if (!esCorrecto) {
                    // ❌ Poner en ROJO la opción seleccionada incorrectamente
                    optionButtons[respuestaUsuarioIndex].setForeground(ERROR_COLOR);

                    // ✅ Poner en VERDE y NEGRITA la opción correcta
                    optionButtons[respuestaCorrectaIndex].setForeground(GREEN_COLOR);
                    optionButtons[respuestaCorrectaIndex].setFont(new Font("Arial", Font.BOLD, 14));

                } else {
                    // ✅ Poner en VERDE y NEGRITA la opción correcta seleccionada
                    optionButtons[respuestaUsuarioIndex].setForeground(GREEN_COLOR);
                    optionButtons[respuestaUsuarioIndex].setFont(new Font("Arial", Font.BOLD, 14));
                }

                // *Esperar 3 segundos antes de avanzar a la siguiente pregunta*
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        currentQuestionIndex++;
                        SwingUtilities.invokeLater(() -> mostrarPregunta());
                    }
                }, 3000); // 3000 ms = 3 segundos
            }
        } else {
            String respuestaUsuario = userInputField.getText().trim();
            esCorrecto = question.checkAnswer(respuestaUsuario);

            // *Aquí agrego la ventana de confirmación para rellenar/ordenar*
            if (esCorrecto) {
                JOptionPane.showMessageDialog(this, "¡Respuesta correcta!", "Correcto", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Respuesta incorrecta.", "Incorrecto", JOptionPane.ERROR_MESSAGE);
            }

            currentQuestionIndex++;
            mostrarPregunta();
        }
    }
}