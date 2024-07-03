package com.team12.DASpring.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;


@ToString
@Data
@Entity
@Table(name = "voucher")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "voucherPercent")
    private int voucherPercent;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "maxVoucher")
    private double maxVoucher;

    @Column(name = "isActive")
    private Boolean isActive;

    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL)
    List<Order> orders;

}
