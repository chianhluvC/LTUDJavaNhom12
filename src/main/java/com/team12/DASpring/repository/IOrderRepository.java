package com.team12.DASpring.repository;

import com.team12.DASpring.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {

    @Query("select p from Order p where p.user.id = ?1")
    public List<Order> getOrderByUserId(Long id);


}
