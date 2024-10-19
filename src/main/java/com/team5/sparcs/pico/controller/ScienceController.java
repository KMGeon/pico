package com.team5.sparcs.pico.controller;

import com.team5.sparcs.pico.application.ScienceService;
import com.team5.sparcs.pico.config.Logging;
import com.team5.sparcs.pico.domain.Scientist;
import com.team5.sparcs.pico.dto.science.response.MainScienceResponse;
import com.team5.sparcs.pico.dto.science.response.PrincipleScienceChatbotResponse;
import com.team5.sparcs.pico.dto.science.response.ScienceDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class ScienceController {

    private final ScienceService scienceService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Logging(action = "GET")
    @GetMapping("/science")
    @Operation(summary = "figma 1", description = "메인 페이지 과학자 이름, 이미지url을 반환한다.")
    public ResponseEntity<List<MainScienceResponse>> selectScientistCard() {
        List<MainScienceResponse> mainScienceResponses = scienceService.selectScientistCard();
        return ResponseEntity.ok(mainScienceResponses);
    }

    @Logging(action = "POST")
    @PostMapping("/science")
    public Scientist createScience(@RequestBody Map<String, String> request) {
        String name = (String) request.get("name");
        String url = (String) request.get("imgUrl");

        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("url", url);
        return scienceService.createScience(map);
    }

    @Logging(action = "GET")
    @GetMapping("/science/detail")
    public ResponseEntity<ScienceDetailResponse> selectScientistDetail(@Parameter(name = "scienceId", description = "과학자 아이디")
                                                                       @RequestParam(value = "scienceId") String scienceId) {
        return ResponseEntity.ok(scienceService.selectScientistDetail(scienceId));
    }

    @Logging(action = "GET")
    @GetMapping("chatbot/principle")
    public ResponseEntity<PrincipleScienceChatbotResponse> selectScientistPrinciple(@RequestParam(value = "scienceId") String scienceId) {

        var chatbotPrinciples = new ArrayList<PrincipleScienceChatbotResponse.ChatbotPrinciple>();

        for (int i = 0; i < 10; i++) {
            PrincipleScienceChatbotResponse.ChatbotPrinciple principle = PrincipleScienceChatbotResponse.ChatbotPrinciple.builder()
                    .principleTitle("title" + i)
                    .principleDesc("desc" + i)
                    .build();

            chatbotPrinciples.add(principle);
        }
        PrincipleScienceChatbotResponse rtn = PrincipleScienceChatbotResponse.builder()
                .scientistName("과학자 이름")
                .scientistImgUrl("img url")
                .chatbotPrinciples(chatbotPrinciples)
                .build();

        return ResponseEntity.ok(rtn);
    }
}