package com.team12.DASpring.controller;


import com.team12.DASpring.services.CartService;
import com.team12.DASpring.services.CategoryService;
import com.team12.DASpring.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;

    @Autowired
    VoucherService voucherService;

    @GetMapping
    public String index(Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
        model.addAttribute("totalNumber",cartService.getSubtotalWithVoucher());
        model.addAttribute("quantityNumber",cartService.getQuantity());
        return "home/index";
    }

    @GetMapping("voucher")
    public String voucherIndex(Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
        model.addAttribute("totalNumber",cartService.getSubtotalWithVoucher());
        model.addAttribute("quantityNumber",cartService.getQuantity());
        model.addAttribute("vouchers",voucherService.getVoucherIsActive());
        return "home/voucher";
    }



}
