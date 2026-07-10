package dev.Gendesson.Study.assistant.dto.question.request;

import dev.Gendesson.Study.assistant.model.enums.AnswerOption;

import java.util.List;

public class QuestionRequestDTO {
    private String statement;

    private AnswerOption userAnswer;

    private AnswerOption correctAnswer;

    private String subject;

    private List<QuestionOptionRequestDTO> questionOptions;
}
