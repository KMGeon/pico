package com.team5.sparcs.pico.dto.history;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team5.sparcs.pico.domain.ChatBotVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryPagingResponse {
    private String scientistName;
    private String chatbotId;
    private String imgUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String createdBy;
    private String summery_chip;

    public static HistoryPagingResponse of(ChatBotVO chatBotVO){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return HistoryPagingResponse.builder()
                .scientistName(chatBotVO.getScientistName())
                .chatbotId(chatBotVO.getChatbot_room_id())
                .imgUrl(chatBotVO.getImgUrl())
                .createdBy(simpleDateFormat.format(chatBotVO.getCreatedBy()))
                .summery_chip(chatBotVO.getSummery_chip())
                .build();
    }
}
