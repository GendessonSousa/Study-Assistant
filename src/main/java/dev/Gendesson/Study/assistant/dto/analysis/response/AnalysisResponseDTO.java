package dev.Gendesson.Study.assistant.dto.analysis.response;

import dev.Gendesson.Study.assistant.model.enums.AnalysisStatus;

public class AnalysisResponseDTO {
    private Long questionId;
    private AnalysisStatus status;
    private String analysis;

    public AnalysisResponseDTO() {
    }

    public AnalysisResponseDTO(Long questionId, AnalysisStatus status, String analysis) {
        this.questionId = questionId;
        this.status = status;
        this.analysis = analysis;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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

}
