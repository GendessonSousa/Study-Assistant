package dev.Gendesson.Study.assistant.dto.question.request;

import dev.Gendesson.Study.assistant.model.enums.AnswerOption;

public class QuestionOptionRequestDTO {
    private AnswerOption letter;

    private String text;

    public QuestionOptionRequestDTO() {
    }

    public AnswerOption getLetter() {
        return letter;
    }

    public void setLetter(AnswerOption letter) {
        this.letter = letter;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
