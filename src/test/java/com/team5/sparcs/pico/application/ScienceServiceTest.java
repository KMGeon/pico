package com.team5.sparcs.pico.application;

import com.team5.sparcs.pico.dto.chatbot.response.ChatbotView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@SpringBootTest
class ScienceServiceTest {

    @Autowired
    private ScienceService scienceService;

    @Test
    public void selectChatBotView() throws Exception{
        // given
        final String name = "뉴턴";
        // when
        ChatbotView chatbotView = scienceService.selectChatBotView(name);

        // then
        System.out.println("chatbotView = " + chatbotView);
    }

}