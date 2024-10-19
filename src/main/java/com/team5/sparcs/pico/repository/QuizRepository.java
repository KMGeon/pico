package com.team5.sparcs.pico.repository;

import com.team5.sparcs.pico.domain.QuizVO;
import com.team5.sparcs.pico.domain.ScienceVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class QuizRepository {

    private final SqlSessionTemplate sqlSessionTemplate;

    public QuizRepository(@Qualifier("sqlSessionTemplate")SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }


    public List<QuizVO> findAll() {
        return sqlSessionTemplate.selectList("quiz.findAll");
    }
}
