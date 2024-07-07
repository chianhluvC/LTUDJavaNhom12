package com.team12.DASpring.controller;


import com.team12.DASpring.entity.ElectronicDevice;
import com.team12.DASpring.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    ElectronicDeviceService electronicDeviceService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CartService cartService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService userService;

    @GetMapping
    public String index(Model model, @RequestParam(name = "pageNo", defaultValue = "1")Integer pageNo){

        model.addAttribute("categories",categoryService.getAllCategories());
        model.addAttribute("totalNumber",cartService.getSubtotalWithVoucher());
        model.addAttribute("quantityNumber",cartService.getQuantity());
        Page<ElectronicDevice> list = electronicDeviceService.getAll(pageNo);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("electronics", list );

        return "shop/shop-list-full";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model){

        model.addAttribute("categories",categoryService.getAllCategories());
        model.addAttribute("totalNumber",cartService.getSubtotalWithVoucher());
        model.addAttribute("quantityNumber",cartService.getQuantity());
        ElectronicDevice electronicDevice = electronicDeviceService.getElectronicDeviceById(id)
                .orElseThrow(()-> new IllegalArgumentException("Không tìm thấy thiết bị có id là: "+id));
        model.addAttribute("electronic", electronicDevice);
        model.addAttribute("reviews", reviewService.getReviewByElectronicId(id));
        if(userService.getUserLogin()==null)
            model.addAttribute("isLogin",false);
        else if (reviewService.getReviewByUserReviewed(id)!=null)
            model.addAttribute("reviewed", reviewService.getReviewByUserReviewed(id));
        return "shop/electronic-details";
    }

    @PostMapping("/search")
    public String search(String deviceSearch ,Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
        model.addAttribute("totalNumber",cartService.getSubtotalWithVoucher());
        model.addAttribute("quantityNumber",cartService.getQuantity());
        if(electronicDeviceService.searchElectronicDevice(deviceSearch)==null)
            model.addAttribute("message", "Không tìm thấy thiết bị: "+deviceSearch);
        else {
            model.addAttribute("electronics", electronicDeviceService.searchElectronicDevice(deviceSearch) );
            model.addAttribute("message", "Thiết bị: "+deviceSearch);
        }

        return "shop/shop-search";
    }

    @GetMapping("/searchCategory")
    public String searchCategory(String category ,Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
        model.addAttribute("totalNumber",cartService.getSubtotalWithVoucher());
        model.addAttribute("quantityNumber",cartService.getQuantity());
        if(electronicDeviceService.searchElectronicDevice(category)==null)
            model.addAttribute("message", "Không tìm Thấy loại thiết bị: "+category);
        else {
            model.addAttribute("electronics", electronicDeviceService.searchElectronicDevice(category) );
            model.addAttribute("message", "Loại thiết bị: "+category);
        }

        return "shop/shop-search";
    }

    @PostMapping("/upsertReview")
    public String addReview(String comment, Long id, int rating){
        if(reviewService.getReviewByUserReviewed(id)!=null)
            reviewService.updateReview(id,comment,rating);
        else
            reviewService.createReview(id,comment,rating);

        return "redirect:/shop/detail/"+id;
    }






}
