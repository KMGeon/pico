package com.team5.sparcs.pico.dto.chatbot.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatbotClickRequest {
    private String step;
    private boolean isClick;
}
