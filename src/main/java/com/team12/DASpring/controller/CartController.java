package com.team12.DASpring.controller;


import com.team12.DASpring.services.CartService;
import com.team12.DASpring.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showCart(Model model){
        if(cartService.checkVoucher(cartService.getVoucherCode()))
            return "redirect:cart/addedVoucher";
        model.addAttribute("categories",categoryService.getAllCategories());
        model.addAttribute("totalNumber",cartService.getSubtotalWithVoucher());
        model.addAttribute("quantityNumber",cartService.getQuantity());
        model.addAttribute("cartItems", cartService.gettCartItems());
        model.addAttribute("subTotal", cartService.getSubtotal());
        model.addAttribute("total", cartService.getSubtotal());
        model.addAttribute("code",cartService.getVoucherCode());
        return "cart/cart";
    }

    @GetMapping("/addedVoucher")
    public String addedVoucher(Model model){

        model.addAttribute("subTotal", cartService.getSubtotal());
        model.addAttribute("total", cartService.getSubtotalWithVoucher());
        model.addAttribute("discount",cartService.getDiscount());
        model.addAttribute("categories",categoryService.getAllCategories());
        model.addAttribute("totalNumber",cartService.getSubtotalWithVoucher());
        model.addAttribute("quantityNumber",cartService.getQuantity());
        model.addAttribute("cartItems", cartService.gettCartItems());
        model.addAttribute("voucherMessage","Voucher đang sử dụng: "+cartService.getVoucherCode());
        return "cart/cart";
    }




    @PostMapping("/addVoucher")
    public String addVoucher(@RequestParam String code, Model model){

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

        model.addAttribute("categories",categoryService.getAllCategories());
        model.addAttribute("totalNumber",cartService.getSubtotalWithVoucher());
        model.addAttribute("quantityNumber",cartService.getQuantity());
        model.addAttribute("cartItems", cartService.gettCartItems());

        return "cart/cart";
    }

    @GetMapping("/add")
    public String addToCart(@RequestParam Long id, @RequestParam int quantity){
        cartService.upsertCart(id, quantity);
        return "redirect:/cart";
    }


    @PostMapping("/upsert")
    public String upsertCart(Long id, int quantity){
        cartService.upsertCart(id, quantity);
        return "redirect:/shop/detail/"+id;
    }

    @PostMapping("/update")
    public String updateCart(Long item_id, int item_quantity){
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
