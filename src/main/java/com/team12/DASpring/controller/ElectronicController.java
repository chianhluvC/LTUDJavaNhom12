package com.team12.DASpring.controller;


import com.team12.DASpring.entity.ElectronicDevice;
import com.team12.DASpring.services.CategoryService;
import com.team12.DASpring.services.DiscountService;
import com.team12.DASpring.services.ElectronicDeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/electronics")
public class ElectronicController {

    @Autowired
    private ElectronicDeviceService electronicDeviceService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DiscountService discountService;

    @GetMapping
    public String showElectronicDeviceList(Model model){
        model.addAttribute("electronics", electronicDeviceService.getAllElectronicDevices() );
        return "/electronic/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("electronic", new ElectronicDevice());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("discounts", discountService.getAllDiscounts());
        return "/electronic/add";
    }

    @PostMapping("/add")
    public String addElectronic(@Valid ElectronicDevice electronicDevice, BindingResult result){
        if(result.hasErrors()){
            return "/electronic/add";
        }
        electronicDeviceService.addElectronicDevice(electronicDevice);
        return "redirect:/electronics";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        ElectronicDevice electronicDevice = electronicDeviceService.getElectronicDeviceById(id)
                .orElseThrow(()-> new  IllegalArgumentException("Invalid electronic Id:"+id));
        model.addAttribute("electronic", electronicDevice);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("discounts", discountService.getAllDiscounts() );

        return "/electronic/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateElectronicDevice(@PathVariable Long id, @Valid ElectronicDevice electronicDevice,
                                         BindingResult result){
        if(result.hasErrors()){
            electronicDevice.setId(id);
            return "/electronic/edit";
        }
        electronicDeviceService.updateElectronicDevice(electronicDevice);

        return "redirect:/electronics";
    }

    @GetMapping("/delete/{id}")
    public String deleteElectronic(@PathVariable Long id){
        electronicDeviceService.deleteElectronicDeviceByID(id);
        return "redirect:/electronics";
    }



}
