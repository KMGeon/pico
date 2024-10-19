package com.team5.sparcs.pico.dto.science.response;

import lombok.Builder;

@Builder
public record ScienceDetailResponse(
        String ScientistName,
        String secondDescription,
        String imgUrl
) {
}
