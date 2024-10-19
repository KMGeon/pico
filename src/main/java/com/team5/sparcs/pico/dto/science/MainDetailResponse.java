package com.team5.sparcs.pico.dto.science;

import com.team5.sparcs.pico.domain.ScienceVO;
import lombok.Builder;

@Builder
public record MainDetailResponse (
        Integer seq,
        String scienceName,
        String backImg
){
    public static MainDetailResponse of(ScienceVO scienceVO){
        return MainDetailResponse.builder()
                .seq(scienceVO.getSeq())
                .scienceName(scienceVO.getScienceName())
                .backImg(scienceVO.getBackImg())
                .build();
    }
}
