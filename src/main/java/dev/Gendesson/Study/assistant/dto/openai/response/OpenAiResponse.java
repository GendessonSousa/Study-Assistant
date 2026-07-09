package dev.Gendesson.Study.assistant.dto.openai.response;

import java.util.List;

public class OpenAiResponse {
    private List<Output> output;

    public List<Output> getOutput() {
        return output;
    }

    public void setOutput(List<Output> output) {
        this.output = output;
    }
}
