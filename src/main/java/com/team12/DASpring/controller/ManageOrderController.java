package com.team12.DASpring.controller;


import com.team12.DASpring.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class ManageOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String Index(Model model){
        model.addAttribute("orders", orderService.getAll());
        return "order/index";
    }

}
