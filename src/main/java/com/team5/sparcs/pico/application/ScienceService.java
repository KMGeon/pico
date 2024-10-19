package com.team5.sparcs.pico.application;

import com.team5.sparcs.pico.domain.Scientist;
import com.team5.sparcs.pico.dto.science.response.MainScienceResponse;
import com.team5.sparcs.pico.dto.science.response.ScienceDetailResponse;
import com.team5.sparcs.pico.repository.ScienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScienceService {

    private final ScienceRepository scienceRepository;


    @Transactional
    public Scientist createScience(HashMap<String, String> map) {
        String name = map.get("name");
        String url = map.get("url");

        return scienceRepository.save(Scientist.builder()
                .name(name)
                .imgUrl(url)
                .build());

    }

    @Transactional(readOnly = true)
    public List<MainScienceResponse> selectScientistCard() {
        return scienceRepository.findAll().stream()
                .map(scientist -> {
                    return MainScienceResponse.of(scientist);
                }).toList();
    }


    @Transactional(readOnly = true)
    public ScienceDetailResponse selectScientistDetail(String scienceId) {
        return ScienceDetailResponse.of(scienceRepository.findById(Long.valueOf(scienceId))
                .orElseThrow(() -> new RuntimeException("exception")));
    }
}
