package com.team5.sparcs.pico.controller;

import com.team5.sparcs.pico.application.QuizService;
import com.team5.sparcs.pico.config.Logging;
import com.team5.sparcs.pico.domain.QuizVO;
import com.team5.sparcs.pico.repository.ScienceRepository;
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
    private final ScienceRepository scienceRepository;
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
            Map<String, Object> scientistInfo = quizService.recommendScientist(answers);
            String scientistName = (String) scientistInfo.get("name");

            Map<String, Object> response = new HashMap<>();
            response.put("name", scientistName);
            response.put("descriptions", scientistInfo.get("descriptions"));
            response.put("compatiblePairs", scientistInfo.get("compatiblePairs"));
            response.put("incompatiblePairs", scientistInfo.get("incompatiblePairs"));



            String rtn ="";
            // 데이터베이스에서 이미지 URL 가져오기
            if (scientistName.equals("아이작 뉴턴")){
                rtn = "뉴턴";
            }else if(scientistName.equals("마리 퀴리")){
                rtn ="마리 퀴리";
            }else if(scientistName.equals("찰스 다윈")){
                rtn = "찰스 다윈";
            }else if (scientistName.equals("갈릴레오 갈릴레이")){
                rtn = "갈릴레오 갈릴레이";
            }else if (scientistName.equals("카를 프리드리히 가우스")){
                rtn  ="가우스";
            }
                //카를 프리드리히 가우스


            String imageUrl = scienceRepository.findImageUrlByName(rtn);
            response.put("imageUrl", imageUrl);

            logger.info("Recommended scientist: {} with image URL: {}", scientistName, imageUrl);
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
