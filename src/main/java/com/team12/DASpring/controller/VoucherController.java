package com.team12.DASpring.controller;



import com.team12.DASpring.entity.Voucher;
import com.team12.DASpring.services.VoucherService;
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
@RequestMapping("/vouchers")
public class VoucherController {

    @Autowired
    private final VoucherService voucherService;

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("voucher", new Voucher());
        return "voucher/add";
    }

    @PostMapping("/add")
    public String addVoucher(@Valid Voucher voucher, BindingResult result){
        if(result.hasErrors()){
            return "voucher/add";
        }
        voucherService.addVoucher(voucher);
        return "redirect:/vouchers";
    }

    @GetMapping
    public String listVouchers(Model model){
        List<Voucher> vouchers = voucherService.getAllVouchers();
        model.addAttribute("vouchers",vouchers);
        return "voucher/list";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model){
        Voucher voucher = voucherService.getVoucher(id)
                .orElseThrow(()->new IllegalStateException("Invalid voucher Id:" + id));
        model.addAttribute("voucher", voucher);
        return "voucher/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateVoucher(@PathVariable("id") Long id, @Valid Voucher voucher,
                                 BindingResult result, Model model){
        if(result.hasErrors()){
            voucher.setId(id);
            return "voucher/edit";
        }
        voucherService.updateVoucher(voucher);
        model.addAttribute("vouchers",voucherService.getAllVouchers());
        return  "redirect:/vouchers";
    }

    @GetMapping("/delete/{id}")
    public String deletevoucher(@PathVariable("id") Long id, Model model){
        Voucher voucher = voucherService.getVoucher(id)
                .orElseThrow(()-> new  IllegalArgumentException("Invalid voucher Id: "+ id));
        voucherService.deleteVoucherById(id);
        model.addAttribute("vouchers", voucherService.getAllVouchers());
        return "redirect:/vouchers";
    }

}
