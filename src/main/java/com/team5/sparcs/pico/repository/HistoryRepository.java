package com.team5.sparcs.pico.repository;

import com.team5.sparcs.pico.domain.ChatBotVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class HistoryRepository {

    private final SqlSessionTemplate sqlSessionTemplate;

    public HistoryRepository(@Qualifier("sqlSessionTemplate")SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public List<ChatBotVO> selectHistoryWithPaging(long offset, Integer limit) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("offset", offset);
        map.put("limit", limit);
        return sqlSessionTemplate.selectList("history.selectHistoryWithPaging", map);
    }

    public long countHistory() {
        return sqlSessionTemplate.selectOne("history.countHistory");
    }

    public ChatBotVO selectHistoryByChatbotId(String chatbotId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("chatbotId", chatbotId);
        return sqlSessionTemplate.selectOne("history.selectHistoryByChatbotId", map);
    }
}
