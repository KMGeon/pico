package com.team5.sparcs.pico.controller;

import com.team5.sparcs.pico.application.ChatService;
import com.team5.sparcs.pico.dto.chatbot.request.ChatbotLogRequest;
import com.team5.sparcs.pico.dto.chatbot.response.SummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @Operation(summary = "챗봇", description = "모든 대화의 AI와 자유롭게 이야기 할 수 있다.")
    @PostMapping("/chat/principle")
    public String chatbotTalk(@RequestBody ChatbotLogRequest chatbotLogRequest){
        return chatService.chatbotTalk(chatbotLogRequest);
    }

    @Operation(summary = "챗봇", description = "모든 내용을 정리한다.")
    @PostMapping("/chat/summery")
    public SummaryResponse summery(@RequestBody ChatbotLogRequest chatbotLogRequest){
        return chatService.summery(chatbotLogRequest);
    }
}
