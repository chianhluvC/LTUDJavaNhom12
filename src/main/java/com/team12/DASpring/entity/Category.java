package com.team12.DASpring.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Vui lòng nhập tên loại")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<ElectronicDevice> electronicDevices;

}
