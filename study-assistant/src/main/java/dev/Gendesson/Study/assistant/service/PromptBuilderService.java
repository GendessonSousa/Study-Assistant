package dev.Gendesson.Study.assistant.service;

import dev.Gendesson.Study.assistant.model.Question;
import dev.Gendesson.Study.assistant.model.QuestionOption;
import org.springframework.stereotype.Service;

@Service
public class PromptBuilderService {
    public String buildQuestionAnalysisPrompt(Question question) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("""
                Analise a seguinte questão.
                Questão:
                """);
        prompt.append(question.getStatement()).append("\n\n");

        prompt.append("Alternativas:\n");

        for (QuestionOption option : question.getQuestionOptions()) {
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
