package com.team5.sparcs.pico.domain;


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

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
}
