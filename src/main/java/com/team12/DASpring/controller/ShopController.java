package com.team12.DASpring.controller;


import com.team12.DASpring.entity.ElectronicDevice;
import com.team12.DASpring.services.ElectronicDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    ElectronicDeviceService electronicDeviceService;

    @GetMapping
    public String index(Model model){
        model.addAttribute("electronics", electronicDeviceService.getAllElectronicDevices() );
        return "shop/shop-list-full";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model){

        ElectronicDevice electronicDevice = electronicDeviceService.getElectronicDeviceById(id)
                .orElseThrow(()-> new IllegalArgumentException("Can't not find electronic with "+id));
        model.addAttribute("electronic", electronicDevice);
        return "shop/electronic-details";
    }

}
