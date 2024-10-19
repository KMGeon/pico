package com.team5.sparcs.pico.controller;

import com.team5.sparcs.pico.application.ScienceService;
import com.team5.sparcs.pico.config.Logging;
import com.team5.sparcs.pico.domain.ScienceVO;
import com.team5.sparcs.pico.dto.chatbot.request.ChatbotLogRequest;
import com.team5.sparcs.pico.dto.chatbot.response.ChatbotView;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScienceController {

    private final ScienceService scienceService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Logging(action = "get")
    @GetMapping("/science")
    public ResponseEntity<List<ScienceVO>> selectScientistCard() {
        return ResponseEntity.ok(scienceService.selectScienceFindAll());
    }

    @Logging(action = "get")
    @GetMapping("/science/detail")
    public ResponseEntity<ScienceVO> selectScienceDetail(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok(scienceService.selectScienceDetail(name));
    }


    //  ================== ch2 chatbot  =======================
    @Logging(action = "get")
    @GetMapping("/chatbot")
    public ResponseEntity<ChatbotView> selectChatBotView(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok(scienceService.selectChatBotView(name));
    }


    @Logging(action = "post")
    @PostMapping("/chatbot")
    public ResponseEntity<String> insertChatBotLog(@RequestBody ChatbotLogRequest chatbotLogRequest){
        String rtn = scienceService.insertChatBotLog(chatbotLogRequest);
        return ResponseEntity.ok(rtn);
    }


}