package com.team5.sparcs.pico.dto.chatbot.response;

import com.team5.sparcs.pico.domain.ScienceVO;
import lombok.Builder;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public record ChatbotView(
        String chatbotId,
        String scienceName,
        String description,
        String birthday,
        String imgUrl,
        List<PrincipleView> principleViews
) {

    public static ChatbotView fromScienceVOList(List<ScienceVO> scienceVOs) {
        if (scienceVOs == null || scienceVOs.isEmpty()) {
            return null;
        }

        ScienceVO firstScience = scienceVOs.get(0);

        List<ChatbotView.PrincipleView> principleViews = scienceVOs.stream()
                .map(vo -> new ChatbotView.PrincipleView(vo.getPrincipleName(), vo.getPrincipleDesc()))
                .collect(Collectors.toList());

        return ChatbotView.builder()
                .chatbotId(generateChatbotId())
                .scienceName(firstScience.getScienceName())
                .description(firstScience.getDescription())
                .birthday(firstScience.getBirthday())
                .imgUrl(firstScience.getImgUrl())
                .principleViews(principleViews)
                .build();
    }

    public record PrincipleView(String principleName, String principleDesc) {
    }
    public static String generateChatbotId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}
