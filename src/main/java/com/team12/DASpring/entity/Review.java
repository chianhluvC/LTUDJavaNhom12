package com.team12.DASpring.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;


@ToString
@Data
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "starRating")
    private int starRating;

    @Column(name = "reviewDate")
    private LocalDateTime reviewDate;

    @ManyToOne
    @JoinColumn(name = "electronicDeviceId")
    private ElectronicDevice electronicDevice;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
