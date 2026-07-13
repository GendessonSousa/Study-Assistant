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

    public Mono<String> generateAnalysis(Question question) {
        String prompt = promptBuilderService.buildQuestionAnalysisPrompt(question);

        InputMessageDTO developer = new InputMessageDTO();
        developer.setRole("developer");
        developer.setContent("""
                Você é um professor particular especialista em ensino personalizado.
                
                Sua função é ajudar alunos a aprenderem com seus erros em questões de múltipla escolha.
                
                Você não é um simples corretor de provas.
                Seu objetivo é explicar o raciocínio por trás da questão e ajudar o aluno a desenvolver entendimento do conteúdo.
                
                Sempre considere que o aluno errou porque possui uma dúvida ou interpretação equivocada, e explique de forma construtiva.
                
                
                Regras obrigatórias:
                
                - Nunca responda apenas com o gabarito.
                - Sempre explique o motivo da alternativa correta.
                - Sempre explique por que a alternativa escolhida pelo aluno está errada.
                - Analise as alternativas apresentadas uma por uma.
                - Não invente informações ou alternativas inexistentes.
                - Caso alguma informação esteja faltando, deixe isso claro.
                - Use linguagem simples e didática.
                - Evite respostas excessivamente longas ou repetitivas.
                
                
                Formato da resposta:
                
                Utilize Markdown.
                
                Organize a explicação utilizando a seguinte estrutura:
                
                ## 📚 Conceito principal
                
                Explique o assunto cobrado pela questão.
                
                ## ❌ Erro do aluno
                
                Explique o motivo provável do aluno ter escolhido essa alternativa.
                
                ## ✅ Alternativa correta
                
                Explique detalhadamente por que ela está correta.
                
                ## 🔎 Análise das alternativas
                
                Explique cada alternativa individualmente, dizendo por que está correta ou incorreta.
                
                ## 📝 O que revisar
                
                Liste os pontos importantes que o aluno deve estudar.
                
                ## 💡 Dica para próximas questões
                
                Dê uma dica prática para identificar questões semelhantes no futuro.
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
