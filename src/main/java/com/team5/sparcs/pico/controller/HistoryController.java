package com.team5.sparcs.pico.controller;

import com.team5.sparcs.pico.application.HistoryService;
import com.team5.sparcs.pico.domain.ChatBotVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @Operation(summary = "도감", description = "request에는 page만 보내기 기본적으로 데이터 9개를 세팅(size)")
    @GetMapping("/history")
    public ResponseEntity<Page<ChatBotVO>> getHistoryWithPaging(
            @PageableDefault(size = 3, sort = "roomId") Pageable pageable) {
        Page<ChatBotVO> result = historyService.getHistoryWithPaging(pageable);
        return ResponseEntity.ok(result);
    }


}
