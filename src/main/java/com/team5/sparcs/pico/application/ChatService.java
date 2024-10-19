package com.team5.sparcs.pico.application;

import com.team5.sparcs.pico.domain.ScienceVO;
import com.team5.sparcs.pico.dto.chatbot.ChatBotLLMRequest;
import com.team5.sparcs.pico.dto.chatbot.request.ChatbotLogRequest;
import com.team5.sparcs.pico.repository.ChatRepository;
import com.team5.sparcs.pico.repository.ScienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ScienceRepository scienceRepository;
    private final RestTemplate restTemplate;

    public String chatbotTalk(ChatbotLogRequest request) {
        String name = request.scientistName();
        String step = request.step();
        String userInput = request.request();
        String chatRoomId = request.chatbotId();

        if (!step.equals("3")) {
            String principleDesc = scienceRepository.findPrompt(name, step).getPrincipleDesc();
            String welcome = scienceRepository.findWelcome(name).getWelcome();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ChatBotLLMRequest build = ChatBotLLMRequest.builder()
                    .welcome(welcome)
                    .scientistName(name)
                    .step(step)
                    .principleDesc(principleDesc)
                    .build();

            HttpEntity<ChatBotLLMRequest> httpRequest = new HttpEntity<>(build, headers);
            String apiUrl = chatRepository.findAPIURL() + "/test";
            System.out.println("apiUrl = " + apiUrl);
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, httpRequest, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                String aiResponse = response.getBody();

                chatRepository.saveLog(userInput, aiResponse, step, chatRoomId);

                return aiResponse;
            } else {
                throw new RuntimeException("API 호출 실패: " + response.getStatusCode());
            }

        }
        if (step.equals("3")) {
            String welcome = scienceRepository.findWelcome(name).getWelcome();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ChatBotLLMRequest build = ChatBotLLMRequest.builder()
                    .welcome(welcome)
                    .scientistName(name)
                    .step(step)
                    .principleDesc("")
                    .build();

            HttpEntity<ChatBotLLMRequest> httpRequest = new HttpEntity<>(build, headers);
            String apiUrl = chatRepository.findAPIURL() + "/test";
            System.out.println("apiUrl = " + apiUrl);
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, httpRequest, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                String aiResponse = response.getBody();

                chatRepository.saveLog(userInput, aiResponse, step, chatRoomId);

                return aiResponse;
            } else {
                throw new RuntimeException("API 호출 실패: " + response.getStatusCode());
            }

        }
        return null;
    }
}
