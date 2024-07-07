package com.team12.DASpring.repository;


import com.team12.DASpring.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Long> {

    @Query("select p from Review p where p.electronicDevice.id = ?1")
    List<Review> getReviewByElectronicDevice_Id(Long id);

    @Query("select p from  Review p where p.user.id = ?1 and p.electronicDevice.id= ?2")
    Review getReviewByElectronicDevice_IdAndUserId(Long userId, Long deviceId);

}
