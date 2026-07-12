package dev.Gendesson.Study.assistant.dto.question.request;

import dev.Gendesson.Study.assistant.model.enums.AnswerOption;

import java.util.List;

public class QuestionRequestDTO {
    private String statement;

    private AnswerOption userAnswer;

    private AnswerOption correctAnswer;

    private String subject;

    private List<QuestionOptionRequestDTO> questionOptions;

    public QuestionRequestDTO() {
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

    public List<QuestionOptionRequestDTO> getQuestionOptions() {
        return questionOptions;
    }

    public void setQuestionOptions(List<QuestionOptionRequestDTO> questionOptions) {
        this.questionOptions = questionOptions;
    }
}
