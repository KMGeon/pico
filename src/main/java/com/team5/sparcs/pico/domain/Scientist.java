package com.team5.sparcs.pico.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Scientist extends BaseEntity {
    private String name;

    @Column(length = 1000)
    private String firstDescription;

    @Column(length = 1000)
    private String secondDescription;

    private String year;

    private String imageUrl;

}
