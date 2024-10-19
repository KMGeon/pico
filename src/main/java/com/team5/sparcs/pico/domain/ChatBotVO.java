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
    private boolean isClickA;
    private boolean isClickB;
    private boolean isClickC;
    private String requestLog;
    private String responseLog;
}
