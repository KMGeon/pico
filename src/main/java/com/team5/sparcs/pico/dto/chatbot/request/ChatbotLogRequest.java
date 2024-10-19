package com.team5.sparcs.pico.dto.chatbot.request;

import lombok.Builder;

@Builder
public record ChatbotLogRequest (
        String chatbotId,
        String scientistName,
        String request,
        String step
){
}
