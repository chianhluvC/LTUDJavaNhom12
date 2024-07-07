package com.team12.DASpring.services;


import com.team12.DASpring.entity.ElectronicDevice;
import com.team12.DASpring.entity.Review;
import com.team12.DASpring.entity.User;
import com.team12.DASpring.repository.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private IReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ElectronicDeviceService electronicDeviceService;

    public List<Review> getAllReview(){
        return reviewRepository.findAll();
    }

    public Review createReview(Long idDevice, String comment, int star){
        User user = userService.getUserLogin();
        ElectronicDevice electronicDevice = electronicDeviceService.getElectronicDeviceById(idDevice).orElseThrow(null);
        Review review = new Review();
        review.setUser(user);
        review.setElectronicDevice(electronicDevice);
        review.setReviewDate(LocalDateTime.now());
        review.setComment(comment);
        review.setStarRating(star);
        reviewRepository.save(review);
        electronicDeviceService.updateStarRatingAndQuantityReview(idDevice,star,1);
        electronicDeviceService.saveAverageStar(idDevice);
        return review;
    }

    public List<Review> getReviewByElectronicId(Long id){
        return reviewRepository.getReviewByElectronicDevice_Id(id);
    }


    public Review getReviewByUserReviewed(Long deviceId){
        return reviewRepository.getReviewByElectronicDevice_IdAndUserId(userService.getUserLogin().getId(), deviceId);
    }

    public Review updateReview(Long id, String comment, int starRating){
        Review review = reviewRepository.getReviewByElectronicDevice_IdAndUserId(userService.getUserLogin().getId(), id);
        int oldStar = review.getStarRating();
        review.setComment(comment);
        review.setStarRating(starRating);
        reviewRepository.save(review);
        electronicDeviceService.updateStarRatingAndQuantityReview(id,starRating-oldStar,0);
        electronicDeviceService.saveAverageStar(id);
        return review;
    }






}
