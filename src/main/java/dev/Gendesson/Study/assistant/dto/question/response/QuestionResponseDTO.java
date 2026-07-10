package dev.Gendesson.Study.assistant.dto.question.response;

import dev.Gendesson.Study.assistant.model.enums.AnalysisStatus;
import dev.Gendesson.Study.assistant.model.enums.AnswerOption;

import java.time.LocalDateTime;
import java.util.List;

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

    public QuestionResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public AnswerOption getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(AnswerOption userAnswer) {
        this.userAnswer = userAnswer;
    }

    public AnswerOption getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(AnswerOption correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public AnalysisStatus getStatus() {
        return status;
    }

    public void setStatus(AnalysisStatus status) {
        this.status = status;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<QuestionOptionResponseDTO> getQuestionOptions() {
        return questionOptions;
    }

    public void setQuestionOptions(List<QuestionOptionResponseDTO> questionOptions) {
        this.questionOptions = questionOptions;
    }
}
