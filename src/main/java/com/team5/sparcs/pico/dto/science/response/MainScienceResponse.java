package com.team5.sparcs.pico.dto.science.response;

import lombok.Builder;

@Builder
public record MainScienceResponse(
        String ScientistName,
        String imgUrl
) {
}
