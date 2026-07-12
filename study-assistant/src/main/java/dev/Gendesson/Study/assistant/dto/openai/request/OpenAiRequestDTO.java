package dev.Gendesson.Study.assistant.dto.openai.request;

import java.util.List;

public class OpenAiRequestDTO {
    private String model;
    private ReasoningDTO reasoning;
    private List<InputMessageDTO> input;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public ReasoningDTO getReasoning() {
        return reasoning;
    }

    public void setReasoning(ReasoningDTO reasoning) {
        this.reasoning = reasoning;
    }

    public List<InputMessageDTO> getInput() {
        return input;
    }

    public void setInput(List<InputMessageDTO> input) {
        this.input = input;
    }
}
