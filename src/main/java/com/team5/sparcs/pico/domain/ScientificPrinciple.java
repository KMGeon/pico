package com.team5.sparcs.pico.domain;


import jakarta.persistence.*;

@Entity
public class ScientificPrinciple extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "principle_id")
    private Long id;


    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    public Scientist scientist;
}
