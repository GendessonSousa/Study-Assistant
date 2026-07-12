package dev.Gendesson.Study.assistant.controller;

import dev.Gendesson.Study.assistant.dto.question.request.QuestionRequestDTO;
import dev.Gendesson.Study.assistant.dto.question.response.QuestionResponseDTO;
import dev.Gendesson.Study.assistant.model.Question;
import dev.Gendesson.Study.assistant.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    //POST
    @PostMapping("/create")
    public ResponseEntity<QuestionResponseDTO> createQuestion(@RequestBody QuestionRequestDTO dto){
        QuestionResponseDTO questionSaved = questionService.saveQuestion(dto);
        return ResponseEntity.ok(questionSaved);
    }

    //GET
    @GetMapping("/list")
    public ResponseEntity<List<QuestionResponseDTO>> listQuestions(){
        List<QuestionResponseDTO> questions = questionService.listQuestions();
        return ResponseEntity.ok(questions);
    }

    //GET (ID)
    @GetMapping("/list/{id}")
    public ResponseEntity<?> listQuestionByID(@PathVariable Long id){
        QuestionResponseDTO question = questionService.listQuestionById(id);

        if (question==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("A questão de ID " + id + " não foi encontrada!");
        }

        return ResponseEntity.ok(question);
    }

    //UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateQuestionById (@PathVariable Long id, @RequestBody QuestionRequestDTO dto){
        QuestionResponseDTO question = questionService.updateQuestion(id, dto);

        if (question == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("A questão de ID " + id + " não foi encontrada!");
        }

        return ResponseEntity.ok(question);
    }

    //DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable Long id){
        boolean deleted = questionService.deleteQuestion(id);

        if (!deleted){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("A Questão de ID " + id + " não foi encontrada");
        }

        return ResponseEntity.ok("A questão de ID " + id + " foi deletada com sucesso!");
    }

}
