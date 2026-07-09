package dev.Gendesson.Study.assistant.controller;

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
    public ResponseEntity<Question> createQuestion(@RequestBody Question question){
        Question questionSave = questionService.saveQuestion(question);
        return ResponseEntity.ok(question);
    }

    //GET
    @GetMapping("/list")
    public ResponseEntity<List<Question>> listQuestions(){
        List<Question> questions = questionService.listQuestions();
        return ResponseEntity.ok(questions);
    }

    //GET (ID)
    @GetMapping("/list/{id}")
    public ResponseEntity<?> listQuestionByID(@PathVariable Long id){
        Question question = questionService.listQuestionById(id);

        if (question==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("A questão de ID " + id + " não foi encontrada!");
        }

        return ResponseEntity.ok(question);
    }

    //UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateQuestionById (@PathVariable Long id, @RequestBody Question questionUpdated){
        Question question = questionService.updateQuestion(id, questionUpdated);

        if (question == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("A questão de ID " + id + " não foi encontrada!");
        }

        return ResponseEntity.ok("A questão de ID: " + id + " foi atualizada com sucesso!");
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
