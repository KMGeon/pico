package com.team5.sparcs.pico.application;

import com.team5.sparcs.pico.domain.ScienceVO;
import com.team5.sparcs.pico.dto.chatbot.ChatBotLLMRequest;
import com.team5.sparcs.pico.dto.chatbot.request.ChatbotLogRequest;
import com.team5.sparcs.pico.repository.ChatRepository;
import com.team5.sparcs.pico.repository.ScienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ChatService {


    private final RestTemplate restTemplate;
    private final ScienceRepository scienceRepository;
    private final ChatRepository chatRepository;


    public String chatbotTalk(ChatbotLogRequest request) {
        String name = request.scientistName();
        String step = request.step();
        String userInput = request.request();
        String chatRoomId = request.chatbotId();

        String welcome = scienceRepository.findWelcome(name).getWelcome();
        String principleDesc = step.equals("3") ? "" : scienceRepository.findPrompt(name, step).getPrincipleDesc();

        ChatBotLLMRequest build = ChatBotLLMRequest.builder()
                .welcome(welcome)
                .scientistName(name)
                .userInput(userInput)
                .step(step)
                .principleDesc(principleDesc)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ChatBotLLMRequest> httpRequest = new HttpEntity<>(build, headers);

        String apiUrl = chatRepository.findAPIURL() + "/get_scientist";
        System.out.println("apiUrl = " + apiUrl);

        ResponseEntity<String> response = sendRequestWithRedirectHandling(apiUrl, httpRequest);

        if (response.getStatusCode().is2xxSuccessful()) {
            String aiResponse = response.getBody();
            chatRepository.saveLog(userInput, aiResponse, step, chatRoomId);
            return aiResponse;
        } else {
            throw new RuntimeException("API 호출 실패: " + response.getStatusCode());
        }
    }

    private ResponseEntity<String> sendRequestWithRedirectHandling(String url, HttpEntity<?> request) {
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        if (response.getStatusCode() == HttpStatus.TEMPORARY_REDIRECT) {
            String newLocation = response.getHeaders().getLocation().toString();
            return restTemplate.exchange(newLocation, HttpMethod.POST, request, String.class);
        }

        return response;
    }
}
