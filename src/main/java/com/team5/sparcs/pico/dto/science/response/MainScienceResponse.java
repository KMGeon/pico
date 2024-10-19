package com.team5.sparcs.pico.dto.science.response;

import com.team5.sparcs.pico.domain.Scientist;
import lombok.Builder;

@Builder
public record MainScienceResponse(
        String ScientistName,
        String imgUrl,
        String firstDescription,
        String year
) {
    public static MainScienceResponse of(Scientist scientist){
        return MainScienceResponse.builder()
                .ScientistName(scientist.getName())
                .imgUrl(scientist.getImgUrl())
                .firstDescription(scientist.getFirstDescription())
                .year(scientist.getYear())
                .build();
    }
}