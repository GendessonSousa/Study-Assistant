package dev.Gendesson.Study.assistant.dto.openai.response;

import java.util.List;

public class OutputDTO {
    private String type;
    private List<ContentDTO> content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ContentDTO> getContent() {
        return content;
    }

    public void setContent(List<ContentDTO> contentDTO) {
        this.content = contentDTO;
    }
}
