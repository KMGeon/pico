package com.team5.sparcs.pico.repository;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository

public class ChatRepository {

    private final SqlSessionTemplate sqlSessionTemplate;

    public ChatRepository(@Qualifier("sqlSessionTemplate") SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public Integer save(String chatbotId, String scienceName) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("chatbotId", chatbotId);
        param.put("scienceName", scienceName);

        return sqlSessionTemplate.insert("chat.save", param);
    }

    public Integer saveLog(String userInput, String aiResponse, String step, String chatRoomId) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userInput", userInput);
        param.put("aiResponse", aiResponse);
        param.put("step", step);
        param.put("chatRoomId", chatRoomId);

        return sqlSessionTemplate.insert("chat.saveLog", param);
    }

    public String findAPIURL() {
        return sqlSessionTemplate.selectOne("chat.findAPIURL");
    }

    public Integer saveSummery(String chatRoomId, String summaryText, String summeryChip) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("chatRoomId", chatRoomId);
        param.put("summaryText", summaryText);
        param.put("summeryChip", summeryChip);
        return sqlSessionTemplate.insert("chat.saveSummery", param);
    }
}
