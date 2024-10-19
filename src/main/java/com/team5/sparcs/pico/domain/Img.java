package com.team5.sparcs.pico.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Img extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_id")
    private Long id;

    private String fileName;
    private String imgUlr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scientist_id")
    public Scientist scientist;
}
