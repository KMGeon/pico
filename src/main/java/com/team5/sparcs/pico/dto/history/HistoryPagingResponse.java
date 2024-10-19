package com.team5.sparcs.pico.dto.history;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team5.sparcs.pico.domain.ChatBotVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryPagingResponse {
    private String scientistName;
    private int chatbotId;
    private String imgUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String createdBy;
    private String summery_chip;

    public static HistoryPagingResponse of(ChatBotVO chatBotVO){
        return HistoryPagingResponse.builder()
                .scientistName(chatBotVO.getScientistName())
                .chatbotId(Integer.parseInt(chatBotVO.getChatbot_id()))
                .imgUrl(chatBotVO.getImgUrl())
                .createdBy(chatBotVO.getCreatedBy())
                .summery_chip(chatBotVO.getSummery_chip())
                .build();
    }
}
