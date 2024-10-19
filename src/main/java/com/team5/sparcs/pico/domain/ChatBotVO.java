package com.team5.sparcs.pico.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatBotVO {
    private Integer roomId;
    private String step;
    private String requestLog;
    private String responseLog;
    private String scientistName;
    private String createdBy;
    private String summation;
}
