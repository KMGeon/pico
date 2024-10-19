package com.team5.sparcs.pico.controller;

import com.team5.sparcs.pico.application.ScienceService;
import com.team5.sparcs.pico.config.Logging;
import com.team5.sparcs.pico.dto.chatbot.request.ChatbotLogRequest;
import com.team5.sparcs.pico.dto.chatbot.response.ChatbotView;
import com.team5.sparcs.pico.dto.history.HistoryPagingResponse;
import com.team5.sparcs.pico.dto.science.MainDetailResponse;
import com.team5.sparcs.pico.dto.science.MainResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScienceController {

    private final ScienceService scienceService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());


    @Operation(summary = "main 1", description = "카드에 모든 과학자 이름, 이미지")
    @Logging(action = "get")
    @GetMapping("/science")
    public ResponseEntity<List<MainResponse>> selectScientistCard() {
        return ResponseEntity.ok(scienceService.selectScienceFindAll());
    }

    @Operation(summary = "main 1-2", description = "1번에서 과학자 선택하면 과학자 이름을 넘겨서 디테일 조회")
    @Logging(action = "get")
    @GetMapping("/science/detail")
    public ResponseEntity<MainDetailResponse> selectScienceDetail(@Parameter(name = "name", description = "과학자 이름") @RequestParam(value = "name") String name) {
        return ResponseEntity.ok(scienceService.selectScienceDetail(name));
    }

    //  ================== ch2 chatbot  =======================
    @Operation(summary = "main 2", description = "이름을 받아서 챗봇 처음에 데이터 전달 VIEW")
    @Logging(action = "get")
    @GetMapping("/chatbot")
    public ResponseEntity<ChatbotView> selectChatBotView(@Parameter(name = "name", description = "과학자 이름") @RequestParam(value = "name") String name) {
        ChatbotView chatbotView = scienceService.selectChatBotView(name);
        return ResponseEntity.ok(chatbotView);
    }


    @Operation(summary = "main 2 - 자유", description = "Step [1,2,3] -> 1 : 원리 1 / 2 = 원리 2 / 3 = 자율 chat")
    @Logging(action = "post")
    @PostMapping("/chatbot")
    public ResponseEntity<String> insertChatBotLog(@Parameter(name = "아이디, 과학자, 요청, step", description = "request는 회원의 input , step은 1,2,3으로 구분해서 각 단계에 요청에 포함되게 처리") @RequestBody ChatbotLogRequest chatbotLogRequest) {
        String rtn = scienceService.insertChatBotLog(chatbotLogRequest);
        return ResponseEntity.ok(rtn);
    }

}
