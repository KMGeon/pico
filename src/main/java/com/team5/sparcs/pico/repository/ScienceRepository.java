package com.team5.sparcs.pico.repository;

import com.team5.sparcs.pico.domain.ScienceVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository

public class ScienceRepository {

    private final SqlSessionTemplate sqlSessionTemplate;

    public ScienceRepository(@Qualifier("sqlSessionTemplate")SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public List<ScienceVO> selectScienceFindAll() {
        return sqlSessionTemplate.selectList("science.selectScienceFindAll");
    }

    public ScienceVO selectScienceDetail(String name) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("name",name);
        return sqlSessionTemplate.selectOne("science.selectScienceDetail", param);
    }

    public List<ScienceVO> selectChatbotView(String name){
        HashMap<String, Object> param = new HashMap<>();
        param.put("name",name);
        return sqlSessionTemplate.selectList("science.selectChatbotView", param);
    }

    public Integer insertRequestChatBotLog(String roomId, String request, String name, String step) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("roomId",roomId);
        param.put("request",request);
        param.put("name",name);
        param.put("step",step);

        return sqlSessionTemplate.insert("science.insertRequestChatBotLog", param);
    }


    public Integer insertResponseChatBotLog(String roomId, String chatbotResponse, String name, String step) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("roomId",roomId);
        param.put("response", chatbotResponse);
        param.put("name",name);
        param.put("step",step);
        return sqlSessionTemplate.insert("science.insertResponseChatBotLog", param);
    }
}
