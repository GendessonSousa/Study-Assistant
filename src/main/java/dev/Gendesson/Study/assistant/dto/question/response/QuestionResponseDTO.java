package dev.Gendesson.Study.assistant.dto.question.response;

import dev.Gendesson.Study.assistant.model.enums.AnalysisStatus;
import dev.Gendesson.Study.assistant.model.enums.AnswerOption;

import java.time.LocalDateTime;

public class QuestionResponseDTO {
    private Long id;

    private String statement;

    private AnswerOption userAnswer;

    private AnswerOption correctAnswer;

    private String subject;

    private AnalysisStatus status;

    private String analysis;

    private LocalDateTime createdAt;

    private List<QuestionOptionResponseDTO> questionOptions;
}
