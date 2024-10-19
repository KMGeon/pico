package com.team5.sparcs.pico.application;

import com.team5.sparcs.pico.domain.ChatBotVO;
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


    public Page<ChatBotVO> getHistoryWithPaging(Pageable pageable) {
        List<ChatBotVO> content = historyRepository.selectHistoryWithPaging(pageable.getOffset(), pageable.getPageSize());
        long total = historyRepository.countHistory();
        return new PageImpl<>(content, pageable, total);
    }
}
