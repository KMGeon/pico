package com.team5.sparcs.pico.application;

import com.team5.sparcs.pico.domain.QuizVO;
import com.team5.sparcs.pico.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    public List<QuizVO> findAll() {
        return quizRepository.findAll();
    }
}
