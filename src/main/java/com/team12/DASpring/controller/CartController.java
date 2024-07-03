package com.team12.DASpring.controller;


import com.team12.DASpring.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public String showCart(Model model){
        model.addAttribute("cartItems", cartService.gettCartItems());
        model.addAttribute("subTotal", cartService.getSubtotal());
        model.addAttribute("total", cartService.getSubtotal());
        return "cart/cart";
    }

    @PostMapping("/addVoucher")
    public String addVoucher(@RequestParam String code, Model model){

        model.addAttribute("cartItems", cartService.gettCartItems());

        if(cartService.checkVoucher(code)){
            cartService.saveVoucher(code);
            model.addAttribute("subTotal", cartService.getSubtotal());
            model.addAttribute("total", cartService.getSubtotalWithVoucher());
            model.addAttribute("discount",cartService.getDiscount());
            model.addAttribute("voucherMessage","Áp voucher: "+cartService.getVoucherCode()+" thành công");
        }
        else{
            model.addAttribute("subTotal", cartService.getSubtotal());
            model.addAttribute("total", cartService.getSubtotal());
            model.addAttribute("voucherMessage","Voucher: "+code+" hết hạn hoặc không tồn tại !");
        }

        return "cart/cart";
    }



    @PostMapping("/upsert")
    public String addToCart(@RequestParam Long id, @RequestParam int quantity){
        cartService.upsertCart(id, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam Long item_id, @RequestParam int item_quantity){
        cartService.updateCart(item_id, item_quantity);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        cartService.removeFormCart(id);
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clearCart(){
        cartService.clearCart();
        return "redirect:/cart";
    }

}
