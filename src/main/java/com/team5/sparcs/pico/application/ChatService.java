package com.team5.sparcs.pico.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.team5.sparcs.pico.dto.chatbot.ChatBotLLMRequest;
import com.team5.sparcs.pico.dto.chatbot.request.ChatbotLogRequest;
import com.team5.sparcs.pico.dto.chatbot.response.SummaryResponse;
import com.team5.sparcs.pico.repository.ChatRepository;
import com.team5.sparcs.pico.repository.ScienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {


    private final RestTemplate restTemplate;
    private final ScienceRepository scienceRepository;
    private final ChatRepository chatRepository;

    private static final String PRINCIPLE_URL = "/scientist";
    private static final String SUMMERY = "/summery";


    @Transactional
    public String chatbotTalk(ChatbotLogRequest request) {
        String name = request.scientistName();
        String step = request.step();
        String userInput = request.request();
        String chatRoomId = request.chatbotId();


        ChatBotLLMRequest build = ChatBotLLMRequest.builder()
                .scientistName(name)
                .userInput(userInput)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ChatBotLLMRequest> httpRequest = new HttpEntity<>(build, headers);

        String apiUrl = chatRepository.findAPIURL() + PRINCIPLE_URL;
        ResponseEntity<String> response = sendRequestWithRedirectHandling(apiUrl, httpRequest);

        if (response.getStatusCode().is2xxSuccessful()) {
            String aiResponse = response.getBody();

            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(aiResponse);
                String extractedResponse = jsonNode.get("response").asText();

                chatRepository.saveLog(userInput, extractedResponse, step, chatRoomId);

                return extractedResponse;
            } catch (Exception e) {
                throw new RuntimeException("응답 파싱 실패: " + e.getMessage());
            }
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

    @Transactional
    public SummaryResponse summery(ChatbotLogRequest request) {
        String name = request.scientistName();
        String userInput = request.request();


        ChatBotLLMRequest build = ChatBotLLMRequest.builder()
                .scientistName(name)
                .userInput(userInput)
                .build();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ChatBotLLMRequest> httpRequest = new HttpEntity<>(build, headers);

        String apiUrl = chatRepository.findAPIURL() + SUMMERY;
        ResponseEntity<String> response = sendRequestWithRedirectHandling(apiUrl, httpRequest);

        if (response.getStatusCode().is2xxSuccessful()) {
            String aiResponse = response.getBody();

            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(aiResponse);

                String summaryText = jsonNode.get("summery").asText();
                String responseText = jsonNode.get("response").asText();

                List<String> responseList = Arrays.stream(responseText.split("\n"))
                        .map(item -> item.replaceFirst("^\\d+\\.\\s*", "").trim())
                        .collect(Collectors.toList());

                SummaryResponse summaryResponse = new SummaryResponse(summaryText, responseList);

                for (String summeryChip : responseList) {
                    chatRepository.saveSummery(request.chatbotId(), summaryText, summeryChip);
                }
                return summaryResponse;
            } catch (Exception e) {
                throw new RuntimeException("응답 파싱 실패: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("API 호출 실패: " + response.getStatusCode());
        }
    }
}