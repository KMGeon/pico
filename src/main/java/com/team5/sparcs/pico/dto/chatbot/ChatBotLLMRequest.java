package com.team5.sparcs.pico.dto.chatbot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatBotLLMRequest {
    @JsonProperty("chatbotId")
    private String chatRoomId;

    @JsonProperty("scientistName")
    private String scientistName;

    @JsonProperty("request")
    private String userInput;

    @JsonProperty("step")
    private String step;

    @JsonProperty("welcome")
    private String welcome;

    @JsonProperty("principleDesc")
    private String principleDesc;
}
