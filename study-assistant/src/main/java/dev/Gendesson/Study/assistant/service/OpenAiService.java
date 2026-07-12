package dev.Gendesson.Study.assistant.service;

import dev.Gendesson.Study.assistant.dto.openai.response.ContentDTO;
import dev.Gendesson.Study.assistant.dto.openai.response.OpenAiResponseDTO;
import dev.Gendesson.Study.assistant.dto.openai.response.OutputDTO;
import dev.Gendesson.Study.assistant.model.Question;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import dev.Gendesson.Study.assistant.dto.openai.request.InputMessageDTO;
import dev.Gendesson.Study.assistant.dto.openai.request.OpenAiRequestDTO;
import dev.Gendesson.Study.assistant.dto.openai.request.ReasoningDTO;

import java.util.List;

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

        InputMessageDTO developer = new InputMessageDTO();
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

        InputMessageDTO user = new InputMessageDTO();
        user.setRole("user");
        user.setContent(prompt);

        ReasoningDTO reasoning = new ReasoningDTO();
        reasoning.setEffort("medium");

        OpenAiRequestDTO request = new OpenAiRequestDTO();
        request.setModel("gpt-5.4-mini");
        request.setReasoning(reasoning);
        request.setInput(List.of(developer, user));

        return webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OpenAiResponseDTO.class)
                .map(this::extractResponse);
    }

    private String extractResponse(OpenAiResponseDTO response) {

        StringBuilder text = new StringBuilder();

        if (response.getOutput() == null) {
            return "Nenhuma resposta encontrada.";
        }

        for (OutputDTO output : response.getOutput()) {
            if (!"message".equals(output.getType())) {
                continue;
            }

            if (output.getContent() == null) {
                continue;
            }

            for (ContentDTO contentDTO : output.getContent()) {
                if ("output_text".equals(contentDTO.getType())) {
                    text.append(contentDTO.getText()).append("\n");
                }

            }
        }

        return text.toString().trim();
    }



}
