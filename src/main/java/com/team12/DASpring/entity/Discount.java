package com.team12.DASpring.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
@Entity
@Table(name = "discount")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "discountPercent")
    private int discountPercent;


    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL)
    List<ElectronicDevice> electronicDevices;



}
