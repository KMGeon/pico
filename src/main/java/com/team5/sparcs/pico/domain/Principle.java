package com.team5.sparcs.pico.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Principle {
    private Integer seq;
    private String principleName;
    private String principleDesc;
}
