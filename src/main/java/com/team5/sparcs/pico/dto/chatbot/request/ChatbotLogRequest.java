package com.team5.sparcs.pico.dto.chatbot.request;

import lombok.Builder;

@Builder
public record ChatbotLogRequest (
        String roomId,
        String request
){
}
