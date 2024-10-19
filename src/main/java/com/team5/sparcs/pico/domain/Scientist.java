package com.team5.sparcs.pico.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Scientist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scientist_id")
    private Long id;

    private String name;
    private String firstDescription;
    private String year;
    private String imgUrl;

    @OneToMany(mappedBy = "scientist")
    private List<ScientificPrinciple> scientificPrinciple = new ArrayList<>();

}
