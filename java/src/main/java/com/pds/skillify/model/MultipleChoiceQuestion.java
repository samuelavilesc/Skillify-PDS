package com.pds.skillify.model;

import java.util.List;

public class MultipleChoiceQuestion extends Question {
    private List<String> options;
    private int correctAnswer;

    public MultipleChoiceQuestion(String statement, List<String> options, int correctAnswer) {
        super(statement);
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean checkAnswer(String answer) {
        try {
            int index = Integer.parseInt(answer);
            return index == correctAnswer;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public List<String> getOptions() {
        return options;
    }
}

