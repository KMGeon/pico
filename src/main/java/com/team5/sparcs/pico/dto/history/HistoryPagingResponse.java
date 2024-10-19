package com.team5.sparcs.pico.dto.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryPagingResponse {
    private String scientistName;
    private int roomId;
    private String imgUrl;
    private String createdBy;
}
