package dev.Gendesson.Study.assistant.dto.openai.response;

import java.util.List;

public class Output {
    private String type;
    private List<Content> content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }
}
