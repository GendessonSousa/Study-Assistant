package dev.Gendesson.Study.assistant.service;

import dev.Gendesson.Study.assistant.model.Question;
import dev.Gendesson.Study.assistant.model.QuestionOption;
import org.springframework.stereotype.Service;

@Service
public class PromptBuilderService {
    public String buildQuestionAnalysisPrompt(Question question) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("""
                Você receberá uma questão de múltipla escolha respondida por um aluno.
                
                Seu objetivo é ensinar o conteúdo da questão, e não apenas informar o gabarito.
                
                Considere que o aluno deseja entender o assunto profundamente para não errar novamente.
                
                ## Questão
                
                """);
        prompt.append(question.getStatement()).append("\n\n");

        prompt.append("Alternativas:\n");

        for (QuestionOption option : question.getQuestionOptions()) {

            if (option.getText() == null || option.getText().isBlank()) {
                continue;
            }

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
                Instruções:
                
                - Analise apenas as alternativas apresentadas acima.
                - Não invente alternativas que não existam.
                - Explique primeiro o conceito principal cobrado pela questão.
                - Explique por que o aluno provavelmente escolheu a alternativa incorreta.
                - Explique por que a alternativa correta está correta.
                - Analise cada alternativa individualmente.
                - Ao final, indique quais assuntos o aluno deve revisar.
                - Finalize com uma dica prática para identificar questões semelhantes.
                
                Organize toda a resposta utilizando Markdown.
                """);

        return prompt.toString();

    }

}
