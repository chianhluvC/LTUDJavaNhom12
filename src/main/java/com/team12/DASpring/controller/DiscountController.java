package com.team12.DASpring.controller;



import com.team12.DASpring.entity.Discount;
import com.team12.DASpring.services.DiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/discounts")
public class DiscountController {

    @Autowired
    private final DiscountService discountService;

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("discount", new Discount());
        return "discount/add";
    }

    @PostMapping("/add")
    public String addDiscount(@Valid Discount discount, BindingResult result){
        if(result.hasErrors()){
            return "discount/add";
        }
        discountService.addDiscount(discount);
        return "redirect:/discounts";
    }

    @GetMapping
    public String listDiscounts(Model model){
        List<Discount> discounts = discountService.getAllDiscounts();
        model.addAttribute("discounts",discounts);
        return "discount/list";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model){
        Discount discount = discountService.getDiscount(id)
                .orElseThrow(()->new IllegalStateException("Invalid discount Id:" + id));
        model.addAttribute("discount", discount);
        return "discount/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateDiscount(@PathVariable("id") Long id, @Valid Discount discount,
                                 BindingResult result, Model model){
        if(result.hasErrors()){
            discount.setId(id);
            return "category/edit";
        }
        discountService.updateDiscount(discount);
        model.addAttribute("discounts",discountService.getAllDiscounts());
        return  "redirect:/discounts";
    }

    @GetMapping("/delete/{id}")
    public String deleteDiscount(@PathVariable("id") Long id, Model model){
        Discount discount = discountService.getDiscount(id)
                .orElseThrow(()-> new  IllegalArgumentException("Invalid discount Id: "+ id));
        discountService.deleteDiscountById(id);
        model.addAttribute("discounts", discountService.getAllDiscounts());
        return "redirect:/discounts";
    }

}
