package com.team5.sparcs.pico.dto.chatbot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatBotLLMRequest {
    private String scientistName;
    private String step;
    private String principleDesc;
    private String welcome;
}
