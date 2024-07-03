package com.team12.DASpring.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;


@ToString
@Data
@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "deviceId")
    private ElectronicDevice electronicDevice;



}
