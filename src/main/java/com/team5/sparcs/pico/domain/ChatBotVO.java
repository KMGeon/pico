package com.team5.sparcs.pico.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatBotVO {
    private String chatbot_id;
    private String scientistName;
    private Date createdBy;
    private String chatbot_log_id;
    private String requestLog;
    private String responseLog;
    private Integer step;
    private String chatbot_summery_id;
    private String summery;
    private String summery_chip;
    private String imgUrl;
    private String chatbot_room_id;
}
