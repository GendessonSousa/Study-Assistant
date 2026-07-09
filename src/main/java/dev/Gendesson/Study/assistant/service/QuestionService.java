package dev.Gendesson.Study.assistant.service;

import dev.Gendesson.Study.assistant.model.Question;
import dev.Gendesson.Study.assistant.model.QuestionOption;
import dev.Gendesson.Study.assistant.model.enums.AnalysisStatus;
import dev.Gendesson.Study.assistant.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question saveQuestion(Question question){
        question.setCreatedAt(LocalDateTime.now());
        question.setStatus(AnalysisStatus.PENDING);

        for (QuestionOption option : question.getQuestionOptions()){
            option.setQuestion(question);
        }

        return questionRepository.save(question);
    }

    public List<Question> listQuestions(){
        return questionRepository.findAll();
    }

    public Question listQuestionById(Long id){
        Optional<Question> questionById = questionRepository.findById(id);
        return questionById.orElse(null);
    }

    public Question updateQuestion(Long id, Question questionUpdated){
        Optional<Question> questionExists = questionRepository.findById(id);
        if (questionExists.isEmpty()){
            return null;
        }

        questionUpdated.setId(id);

        return questionRepository.save(questionUpdated);
    }

    public boolean deleteQuestion(Long id){
        if (!questionRepository.existsById(id)){
            return false;
        }
        questionRepository.deleteById(id);
        return true;
    }




}
