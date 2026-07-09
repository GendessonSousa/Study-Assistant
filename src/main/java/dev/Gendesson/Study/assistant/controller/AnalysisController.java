package dev.Gendesson.Study.assistant.controller;

import dev.Gendesson.Study.assistant.service.OpenAiService;
import dev.Gendesson.Study.assistant.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AnalysisController {
    private QuestionService questionService;

    public AnalysisController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/question/{id}/analysis")
    public Mono<ResponseEntity<String>> generateAnalysis(@PathVariable Long id) {
        return questionService.generateAnalysis(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }
}
