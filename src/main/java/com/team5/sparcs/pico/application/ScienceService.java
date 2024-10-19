package com.team5.sparcs.pico.application;

import com.team5.sparcs.pico.domain.ScienceVO;
import com.team5.sparcs.pico.dto.chatbot.request.ChatbotLogRequest;
import com.team5.sparcs.pico.dto.chatbot.response.ChatbotView;
import com.team5.sparcs.pico.repository.ScienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScienceService {

    private final ScienceRepository scienceRepository;
    private final OpenAiChatModel openAiChatModel;




    public List<ScienceVO> selectScienceFindAll() {
        return scienceRepository.selectScienceFindAll();
    }

    public ScienceVO selectScienceDetail(String name) {
        return scienceRepository.selectScienceDetail(name);
    }

    public ChatbotView selectChatBotView(String name) {

        List<ScienceVO> scienceVOS = scienceRepository.selectChatbotView(name);

        return ChatbotView.fromScienceVOList(scienceVOS);
    }

    public String insertChatBotLog(ChatbotLogRequest chatbotLogRequest) {
        String roomId = chatbotLogRequest.roomId();
        String request = chatbotLogRequest.request();
        Integer i = scienceRepository.insertRequestChatBotLog(roomId, request);

        String chatbotResponse = chatbot(request);
        scienceRepository.insertResponseChatBotLog(roomId, chatbotResponse);
        return chatbotResponse;
    }


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
