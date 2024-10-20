package com.team5.sparcs.pico.controller;

import com.team5.sparcs.pico.application.HistoryService;
import com.team5.sparcs.pico.domain.ChatBotVO;
import com.team5.sparcs.pico.dto.history.HistoryPagingResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @Operation(summary = "도감", description = "request에는 page만 보내기 기본적으로 데이터 9개를 세팅(size)")
    @GetMapping("/history")
    public ResponseEntity<Page<HistoryPagingResponse>> getHistoryWithPaging(
            @PageableDefault(size = 9, sort = "roomId") Pageable pageable) {
        Page<HistoryPagingResponse> result = historyService.getHistoryWithPaging(pageable);
        return ResponseEntity.ok(result);
    }
    @GetMapping("history/detail")
    public Map<String, Object> selectHistoryByChatbotId(@RequestParam(value = "chatbotId") String chatbotId) {
        List<ChatBotVO> chatBotVOS = historyService.selectHistoryByChatbotId(chatbotId);

        if (chatBotVOS.isEmpty()) {
            return Collections.emptyMap();
        }

        ChatBotVO firstChatBot = chatBotVOS.get(0);
        Map<String, Object> result = new HashMap<>();
        result.put("chatbot_id", firstChatBot.getChatbot_id());
        result.put("summery", firstChatBot.getSummery());

        List<String> summeryChips = chatBotVOS.stream()
                .map(ChatBotVO::getSummery_chip)
                .collect(Collectors.toList());

        result.put("summery_chips", summeryChips);

        return result;
    }

}
