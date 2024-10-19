package com.team5.sparcs.pico.dto.science.response;

import com.team5.sparcs.pico.domain.Scientist;
import lombok.Builder;

@Builder
public record ScienceDetailResponse(
        String name,
        String firstDescription,
        String year,
        String imgUrl
) {
    public static ScienceDetailResponse of(Scientist scientist){
        return ScienceDetailResponse.builder()
                .name(scientist.getName())
                .firstDescription(scientist.getFirstDescription())
                .year(scientist.getYear())
                .imgUrl(scientist.getImgUrl())
                .build();
    }
}