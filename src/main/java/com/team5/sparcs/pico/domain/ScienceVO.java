package com.team5.sparcs.pico.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScienceVO {
    private Integer seq;
    private String scienceName;
    private String description;
    private String imgUrl;
    private String backImg;
    private String chatImg;

    //=========
    private String principleName;
    private String principleDesc;
}
