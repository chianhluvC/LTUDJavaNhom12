package com.team12.DASpring.entity;


import com.team12.DASpring.Validator.annotation.ValidCategoryId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;


import javax.swing.text.Style;
import java.util.List;

@Data
@ToString
@Entity
@Table(name = "electronic_device")
public class ElectronicDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "image1")
    private String image1;

    @Column(name = "image2")
    private String image2;

    @Column(name = "image3")
    private String image3;

    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @Column(name = "discountPrice")
    private Double discountPrice;

    @Column(name = "isActive")
    private Boolean isActive;

    @Column(name = "starReview")
    private int starReview;

    @Column(name = "quantityReview")
    private int quantityReview;

    @Column(name = "averageStar")
    private int averageStar;


    @ManyToOne
    @JoinColumn(name = "category_id")
    @ValidCategoryId
    private Category category;

    @OneToMany(mappedBy = "electronicDevice", cascade = CascadeType.ALL)
    List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @OneToMany(mappedBy = "electronicDevice", cascade = CascadeType.ALL)
    List<Review> reviews;


}
