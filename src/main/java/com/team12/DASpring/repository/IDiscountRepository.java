package com.team12.DASpring.repository;


import com.team12.DASpring.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDiscountRepository extends JpaRepository<Discount,Long> {
}
