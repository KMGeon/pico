package com.team5.sparcs.pico.application;

import com.team5.sparcs.pico.dto.science.response.MainScienceResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@SpringBootTest
class ScienceServiceTest {

    @Autowired
    private ScienceService scienceService;


}