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

    public OpenAiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> generateAnalysis(Question question){
        String prompt = buildPrompt(question);

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

    private String buildPrompt(Question question){
        StringBuilder prompt = new StringBuilder();
        prompt.append("""
                Analise a seguinte questão.
                Questão:
                """);
        prompt.append(question.getStatement()).append("\n\n");

        prompt.append("Alternativas:\n");

        for (QuestionOption option: question.getQuestionOptions()){
            prompt.append(option.getLetter())
                    .append(") ")
                    .append(option.getText())
                    .append("\n");
        }

        prompt.append("\n");

        prompt.append("Resposta escolhida pelo aluno: ")
                .append(question.getUserAnswer())
                .append("\n");

        prompt.append("Resposta correta: ")
                .append(question.getCorrectAnswer())
                .append("\n\n");

        prompt.append("""
                Explique:
                
                1. Por que a resposta escolhida está incorreta.
                
                2. Por que a alternativa correta é a certa.
                
                3. Explique o conteúdo teórico necessário para acertar essa questão.
                
                4. Analise cada alternativa, dizendo por que está certa ou errada.
                
                5. Dê uma dica prática para que o aluno não cometa esse erro novamente.
                
                Seja extremamente didático e utilize Markdown.
                """);

        return prompt.toString();
    }

}
