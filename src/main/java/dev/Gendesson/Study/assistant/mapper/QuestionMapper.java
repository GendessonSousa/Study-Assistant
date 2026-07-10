package dev.Gendesson.Study.assistant.mapper;

import dev.Gendesson.Study.assistant.dto.question.request.QuestionOptionRequestDTO;
import dev.Gendesson.Study.assistant.dto.question.request.QuestionRequestDTO;
import dev.Gendesson.Study.assistant.dto.question.response.QuestionOptionResponseDTO;
import dev.Gendesson.Study.assistant.dto.question.response.QuestionResponseDTO;
import dev.Gendesson.Study.assistant.model.Question;
import dev.Gendesson.Study.assistant.model.QuestionOption;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionMapper {
    public Question toEntity(QuestionRequestDTO dto){
        Question question = new Question();

        question.setStatement(dto.getStatement());
        question.setSubject(dto.getSubject());
        question.setUserAnswer(dto.getUserAnswer());
        question.setCorrectAnswer(dto.getCorrectAnswer());

        List<QuestionOption> options = new ArrayList<>();
        if (dto.getQuestionOptions() != null){
            for(QuestionOptionRequestDTO optionDTO : dto.getQuestionOptions()){
                options.add((toEntity(optionDTO, question)));
            }
        }

        question.setQuestionOptions(options);

        return question;
    }

    public QuestionResponseDTO toResponse(Question question){
        QuestionResponseDTO dto = new QuestionResponseDTO();

        dto.setId(question.getId());
        dto.setStatement(question.getStatement());
        dto.setSubject(question.getSubject());
        dto.setUserAnswer(question.getUserAnswer());
        dto.setCorrectAnswer(question.getCorrectAnswer());
        dto.setAnalysis(question.getAnalysis());
        dto.setStatus(question.getStatus());
        dto.setCreatedAt(question.getCreatedAt());

        List<QuestionOptionResponseDTO> options = new ArrayList<>();

        if (question.getQuestionOptions() != null){
            for (QuestionOption option : question.getQuestionOptions()){
                options.add(toResponse(option));
            }
        }

        dto.setQuestionOptions(options);

        return dto;
    }

    private QuestionOption toEntity(QuestionOptionRequestDTO dto, Question question){
        QuestionOption option = new QuestionOption();

        option.setLetter(dto.getLetter());
        option.setText(dto.getText());
        option.setQuestion(question);

        return option;
    }

    private QuestionOptionResponseDTO toResponse(QuestionOption option){
        QuestionOptionResponseDTO dto = new QuestionOptionResponseDTO();

        dto.setLetter(option.getLetter());
        dto.setText(option.getText());

        return dto;
    }
}
