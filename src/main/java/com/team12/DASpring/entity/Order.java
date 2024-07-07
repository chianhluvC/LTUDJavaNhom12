package com.team12.DASpring.entity;


import com.team12.DASpring.Validator.annotation.ValidUserId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customerName")
    private String customerName;

    @Column(name = "address")
    private String address;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "total")
    private double total;

    @Column(name = "message")
    private String message;


    @Column(name = "orderDate")
    private LocalDateTime orderDate;

    @Column(name = "orderStatus")
    private String orderStatus;

    @Column(name = "discountPrice")
    private double discountPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @ValidUserId
    private  User user;

    @ManyToOne
    @JoinColumn(name = "voucherId")
    private Voucher voucher;

}
