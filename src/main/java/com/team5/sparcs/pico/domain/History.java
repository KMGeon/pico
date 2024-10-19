package com.team5.sparcs.pico.domain;

import jakarta.persistence.*;

@Entity
public class History extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    private String summation;
}
