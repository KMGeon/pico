package com.team5.sparcs.pico.controller;

import com.team5.sparcs.pico.application.ChatService;
import com.team5.sparcs.pico.dto.chatbot.request.ChatbotLogRequest;
import com.team5.sparcs.pico.dto.chatbot.response.SummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/chat/principle")
    public String chatbotTalk(@RequestBody ChatbotLogRequest chatbotLogRequest){
        return chatService.chatbotTalk(chatbotLogRequest);
    }

    @PostMapping("/chat/summery")
    public SummaryResponse summery(@RequestBody ChatbotLogRequest chatbotLogRequest){
        return chatService.summery(chatbotLogRequest);
    }
}
