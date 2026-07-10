package dev.Gendesson.Study.assistant.dto.openai.response;

import java.util.List;

public class OpenAiResponseDTO {
    private List<OutputDTO> output;

    public List<OutputDTO> getOutput() {
        return output;
    }

    public void setOutput(List<OutputDTO> output) {
        this.output = output;
    }
}
