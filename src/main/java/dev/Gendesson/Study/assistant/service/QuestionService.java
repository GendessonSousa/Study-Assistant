package dev.Gendesson.Study.assistant.service;

import dev.Gendesson.Study.assistant.dto.question.request.QuestionOptionRequestDTO;
import dev.Gendesson.Study.assistant.dto.question.request.QuestionRequestDTO;
import dev.Gendesson.Study.assistant.dto.question.response.QuestionResponseDTO;
import dev.Gendesson.Study.assistant.mapper.QuestionMapper;
import dev.Gendesson.Study.assistant.model.Question;
import dev.Gendesson.Study.assistant.model.QuestionOption;
import dev.Gendesson.Study.assistant.model.enums.AnalysisStatus;
import dev.Gendesson.Study.assistant.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final OpenAiService openAiService;
    private final QuestionMapper questionMapper;

    public QuestionService(QuestionMapper questionMapper, OpenAiService openAiService, QuestionRepository questionRepository) {
        this.questionMapper = questionMapper;
        this.openAiService = openAiService;
        this.questionRepository = questionRepository;
    }

    public QuestionResponseDTO saveQuestion(QuestionRequestDTO dto){
        Question question = questionMapper.toEntity(dto);

        question.setCreatedAt(LocalDateTime.now());
        question.setStatus(AnalysisStatus.PENDING);

        Question savedQuestion = questionRepository.save(question);

        return questionMapper.toResponse(savedQuestion);
    }

    public List<QuestionResponseDTO> listQuestions(){
        return questionRepository.findAll()
                .stream()
                .map(questionMapper::toResponse)
                .toList();
    }

    public QuestionResponseDTO listQuestionById(Long id){
        return questionRepository.findById(id)
                .map(questionMapper::toResponse)
                .orElse(null);
    }

    public QuestionResponseDTO updateQuestion(Long id, QuestionRequestDTO dto){
        Question question = questionRepository.findById(id)
                .orElse(null);

        if (question == null){
            return null;
        }

        question.setStatement(dto.getStatement());
        question.setSubject(dto.getSubject());
        question.setUserAnswer(dto.getUserAnswer());
        question.setCorrectAnswer(dto.getCorrectAnswer());

        question.getQuestionOptions().clear();

        if (dto.getQuestionOptions() != null){

            for (QuestionOptionRequestDTO optionDTO : dto.getQuestionOptions()){
                QuestionOption option = new QuestionOption();

                option.setLetter(optionDTO.getLetter());
                option.setText(optionDTO.getText());
                option.setQuestion(question);

                question.getQuestionOptions().add(option);
            }
        }

        Question savedQuestion = questionRepository.save(question);

        return questionMapper.toResponse(savedQuestion);
    }

    public boolean deleteQuestion(Long id){
        if (!questionRepository.existsById(id)){
            return false;
        }
        questionRepository.deleteById(id);
        return true;
    }

    public Mono<String> generateAnalysis(Long id){
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Questão não encontrada."));

        if(question.getAnalysis() != null&&
                !question.getAnalysis().isBlank()){
            return Mono.just(question.getAnalysis());
        }

        return openAiService.generateAnalysis(question)
                .map(analysis -> {
                    question.setAnalysis(analysis);
                    question.setStatus(AnalysisStatus.COMPLETED);

                    questionRepository.save(question);

                    return analysis;
                });
    }

}
