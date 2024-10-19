package com.team5.sparcs.pico.application;

import com.team5.sparcs.pico.domain.ScienceVO;
import com.team5.sparcs.pico.dto.chatbot.request.ChatbotLogRequest;
import com.team5.sparcs.pico.dto.chatbot.response.ChatbotView;
import com.team5.sparcs.pico.repository.ScienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return ChatbotView.fromScienceVOList(scienceRepository.selectChatbotView(name));
    }

    @Transactional
    public String insertChatBotLog(ChatbotLogRequest chatbotLogRequest) {
        String roomId = chatbotLogRequest.roomId();
        String request = chatbotLogRequest.request();
        String name = chatbotLogRequest.scientistName();

        scienceRepository.insertRequestChatBotLog(roomId, request, name);
        String chatbotResponse = chatbot(request, name);

        scienceRepository.insertResponseChatBotLog(roomId, chatbotResponse, name);
        return chatbotResponse;
    }


    public String chatbot(String request, String name) {
        PromptTemplate promptTemplate = new PromptTemplate(
                "당신은 {scientistName} 모든 지식을 전문으로 다뤄." +
                        "사용자에 말에 일상적인 대화부터 전문적인 지식까지 요청에 따라 답해줘" +
                        "사용자 : 배가 너무 아파요\n" +
                        "답변 : 배탈인 것 같습니다.\n" +
                        "이런 형식으로 대답해줘\n" +
                        "환자: {message}\n"
        );

        String prompt = String.valueOf(promptTemplate.create(Map.of(
                "message", request,
                "scientistName", name
        )));

        return openAiChatModel.call(prompt);
    }
}
