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
    @JsonProperty("scientistName")
    private String scientistName;

    @JsonProperty("request")
    private String userInput;

}
