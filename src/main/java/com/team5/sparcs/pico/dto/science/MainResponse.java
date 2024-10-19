package com.team5.sparcs.pico.dto.science;


import com.team5.sparcs.pico.domain.ScienceVO;
import lombok.Builder;

@Builder
public record MainResponse (
        Integer seq,
        String scienceName,
        String description,
        String imgUrl
){
    public static MainResponse of(ScienceVO scienceVO){
        return MainResponse.builder()
                .seq(scienceVO.getSeq())
                .scienceName(scienceVO.getScienceName())
                .description(scienceVO.getDescription())
                .imgUrl(scienceVO.getImgUrl())
                .build();
    }
}
