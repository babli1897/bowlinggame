package com.target.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "lane")
public class Lane {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String laneStatus;
}
