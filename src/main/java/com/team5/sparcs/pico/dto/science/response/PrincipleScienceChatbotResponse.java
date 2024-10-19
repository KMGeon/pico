package com.team5.sparcs.pico.dto.science.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PrincipleScienceChatbotResponse(
        String scientistName,
        String scientistImgUrl,
        List<ChatbotPrinciple> chatbotPrinciples
) {

    @Builder
    public record ChatbotPrinciple(
            String principleTitle,
            String principleDesc
    ) {

    }
}
