package com.team5.sparcs.pico.application;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiService {
    private final OpenAiChatModel openAiChatModel;
    public String chatbot(String request) {
        PromptTemplate promptTemplate = new PromptTemplate(
                "당신은 의사야 그래서 환자의 증상을 바탕으로 환저의 병을 예측해줘" +
                        "환자가 자신의 증상을 말하면 너의 지식으로 잘 최대한 친절하게 알려줘." +
                        "환자 : 배가 너무 아파요\n" +
                        "답변 : 배탈인 것 같습니다.\n" +
                        "이런 형식으로 대답해줘\n" +
                        "환자: {message}\n"
        );

        String prompt = String.valueOf(promptTemplate.create(Map.of("message", request)));

        return openAiChatModel.call(prompt);
    }
}