package com.team5.sparcs.pico.controller;

import com.team5.sparcs.pico.application.QuizService;
import com.team5.sparcs.pico.config.Logging;
import com.team5.sparcs.pico.domain.QuizVO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Logging(action = "GET")
    @GetMapping
    public ResponseEntity<List<QuizVO>> selectQuiz() {
        logger.info("Fetching all quizzes");
        List<QuizVO> quizzes = quizService.findAll();
        return ResponseEntity.ok(quizzes);
    }

    @Logging(action = "POST")
    @PostMapping("/recommend")
    public ResponseEntity<Map<String, Object>> getRecommendation(@RequestBody List<Integer> answers) {
        logger.info("Receiving recommendation request with {} answers", answers.size());
        try {
            QuizService.Scientist scientist = quizService.recommendScientist(answers);
            Map<String, Object> response = new HashMap<>();
            response.put("name", scientist.getName());
            response.put("descriptions", scientist.getDescriptions());
            logger.info("Recommended scientist: {}", scientist.getName());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Error in recommendation: {}", e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            logger.error("Unexpected error in recommendation", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "An unexpected error occurred");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}