package dev.Gendesson.Study.assistant.service;

import dev.Gendesson.Study.assistant.dto.openai.response.Content;
import dev.Gendesson.Study.assistant.dto.openai.response.OpenAiResponse;
import dev.Gendesson.Study.assistant.dto.openai.response.Output;
import dev.Gendesson.Study.assistant.model.Question;
import dev.Gendesson.Study.assistant.model.QuestionOption;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import dev.Gendesson.Study.assistant.dto.openai.request.InputMessage;
import dev.Gendesson.Study.assistant.dto.openai.request.OpenAiRequest;
import dev.Gendesson.Study.assistant.dto.openai.request.Reasoning;

import java.util.List;
import java.util.Map;

@Service
public class OpenAiService {
    private final WebClient webClient;
    private String apiKey = System.getenv("API_KEY");
    private final PromptBuilderService promptBuilderService;

    public OpenAiService(WebClient webClient, PromptBuilderService promptBuilderService) {
        this.webClient = webClient;
        this.promptBuilderService = promptBuilderService;
    }

    public Mono<String> generateAnalysis(Question question){
        String prompt = promptBuilderService.buildQuestionAnalysisPrompt(question);

        InputMessage developer = new InputMessage();
        developer.setRole("developer");
        developer.setContent("""
                Você é um professor especialista em ensino.
                
                Explique de forma simples.
                
                Nunca entregue apenas o gabarito.
                
                Analise todas as alternativas.
                
                Sempre utilize Markdown.
                
                Explique como se estivesse ensinando um aluno que acabou de errar uma prova.
                
                Seja claro, objetivo e didático.
                """);

        InputMessage user = new InputMessage();
        user.setRole("user");
        user.setContent(prompt);

        Reasoning reasoning = new Reasoning();
        reasoning.setEffort("medium");

        OpenAiRequest request = new OpenAiRequest();
        request.setModel("gpt-5.4-mini");
        request.setReasoning(reasoning);
        request.setInput(List.of(developer, user));

        return webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OpenAiResponse.class)
                .map(this::extractResponse);
    }

    private String extractResponse(OpenAiResponse response) {

        StringBuilder text = new StringBuilder();

        if (response.getOutput() == null) {
            return "Nenhuma resposta encontrada.";
        }

        for (Output output : response.getOutput()) {
            if (!"message".equals(output.getType())) {
                continue;
            }

            if (output.getContent() == null) {
                continue;
            }

            for (Content content : output.getContent()) {
                if ("output_text".equals(content.getType())) {
                    text.append(content.getText()).append("\n");
                }

            }
        }

        return text.toString().trim();
    }



}
