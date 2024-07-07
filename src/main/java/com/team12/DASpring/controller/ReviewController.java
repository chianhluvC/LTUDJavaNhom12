package com.team12.DASpring.controller;


import com.team12.DASpring.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/reviews")
@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public String index(Model model){

        model.addAttribute("reviews",reviewService.getAllReview());
        return "review/index";
    }



}
