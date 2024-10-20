package com.team5.sparcs.pico.application;

import com.team5.sparcs.pico.domain.ChatBotVO;
import com.team5.sparcs.pico.dto.history.HistoryPagingResponse;
import com.team5.sparcs.pico.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;


    public Page<HistoryPagingResponse> getHistoryWithPaging(Pageable pageable) {
        List<HistoryPagingResponse> list = historyRepository.selectHistoryWithPaging(pageable.getOffset(), pageable.getPageSize()).stream()
                .map(chatBotVO -> {
                    return HistoryPagingResponse.of(chatBotVO);
                }).toList();
        long total = historyRepository.countHistory();
        return new PageImpl<>(list, pageable, total);
    }

    public List<ChatBotVO> selectHistoryByChatbotId(String chatbotId) {
        return historyRepository.selectHistoryByChatbotId(chatbotId);
    }
}
