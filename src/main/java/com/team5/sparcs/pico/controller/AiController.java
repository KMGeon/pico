package com.team5.sparcs.pico.controller;

import com.team5.sparcs.pico.application.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/test")
    public String chatbot(@RequestBody Map<String, String> request) {
        return aiService.chatbot(request.get("request"));
    }

}
