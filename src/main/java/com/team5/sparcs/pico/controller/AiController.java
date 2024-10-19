package com.team5.sparcs.pico.controller;

import com.team5.sparcs.pico.application.AiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @Operation(summary = "AI 질문하기", description = "{'request':''}에 질문하면 Chatgpt를 통해 반환한다.")
    @PostMapping("/ai")
    public String chatbot(@RequestBody Map<String, String> request) {
        return aiService.chatbot(request.get("request"));
    }
}
