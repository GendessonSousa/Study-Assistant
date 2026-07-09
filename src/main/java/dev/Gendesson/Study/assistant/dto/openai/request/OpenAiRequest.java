package dev.Gendesson.Study.assistant.dto.openai.request;

import java.util.List;

public class OpenAiRequest {
    private String model;
    private Reasoning reasoning;
    private List<InputMessage> input;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Reasoning getReasoning() {
        return reasoning;
    }

    public void setReasoning(Reasoning reasoning) {
        this.reasoning = reasoning;
    }

    public List<InputMessage> getInput() {
        return input;
    }

    public void setInput(List<InputMessage> input) {
        this.input = input;
    }
}
