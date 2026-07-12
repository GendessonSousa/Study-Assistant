package dev.Gendesson.Study.assistant.controller;

import dev.Gendesson.Study.assistant.dto.analysis.response.AnalysisResponseDTO;
import dev.Gendesson.Study.assistant.service.OpenAiService;
import dev.Gendesson.Study.assistant.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class AnalysisController {
    private QuestionService questionService;

    public AnalysisController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/question/{id}/analysis")
    public Mono<ResponseEntity<AnalysisResponseDTO>> generateAnalysis(@PathVariable Long id) {
        return questionService.generateAnalysis(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }
}
