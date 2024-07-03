package com.team12.DASpring.services;


import com.team12.DASpring.entity.Cart;
import com.team12.DASpring.entity.CartItem;
import com.team12.DASpring.entity.ElectronicDevice;
import com.team12.DASpring.entity.Voucher;
import com.team12.DASpring.repository.IElectronicDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDate;
import java.util.List;

@Service
@SessionScope
public class CartService {

    final Cart cart = new Cart();

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private IElectronicDeviceRepository electronicDeviceRepository;
    @Autowired
    private DiscountService discountService;

    public void upsertCart(Long electronicId, int quantity){
        ElectronicDevice electronicDevice = electronicDeviceRepository.findById(electronicId)
                .orElseThrow(()->new IllegalArgumentException("Electronlic device not found: "+electronicId));
        if(cart.cartItems.stream().anyMatch(p -> p.gettElectronicDevice().getId().equals(electronicId) && p.gettQuantity() > 0)){
            CartItem item = cart.cartItems.stream().filter(p->p.gettElectronicDevice().getId().equals(electronicId)).findFirst().orElseThrow(null);
            if(item!=null)
                item.settQuantity(item.gettQuantity() + quantity);
        }
        else
            cart.cartItems.add(new CartItem(electronicDevice, quantity));

    }

    public void updateCart(Long id, int quantity){
        ElectronicDevice electronicDevice = electronicDeviceRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Electronlic device not found: "+id));
        if(cart.cartItems.stream().anyMatch(p -> p.gettElectronicDevice().getId().equals(id) && p.gettQuantity() > 0)){
            CartItem item = cart.cartItems.stream().filter(p->p.gettElectronicDevice().getId().equals(id)).findFirst().orElseThrow(null);
            if(item!=null){
                item.settQuantity(quantity);
            }

        }
    }


    public List<CartItem> gettCartItems(){
        return cart.cartItems;
    }

    public String getVoucherCode(){
        return cart.voucherCode;
    }

    public void removeFormCart(Long electronicId){
        cart.cartItems.removeIf(i->i.gettElectronicDevice().getId().equals(electronicId));
    }


    public void clearCart(){
        cart.cartItems.clear();
    }

    public double getSubtotal(){
        return cart.cartItems.stream().filter(p->p.getAmount()>0).mapToDouble(CartItem::getAmount).sum();
    }

    public double getSubtotalWithVoucher(){
        double total = getSubtotal();
        Voucher voucher = voucherService.getVoucherByCode(cart.voucherCode).orElseThrow(null);
        if(voucher==null||voucher.getEndDate()==null)
            return total;
        if(LocalDate.now().isBefore(voucher.getEndDate())||LocalDate.now().equals(voucher.getEndDate())&&voucher.getIsActive())
        {
            double discount = total * (100-voucher.getVoucherPercent())/100;
            if(discount >= voucher.getMaxVoucher())
                total = total - voucher.getMaxVoucher();
            else
                total = discount;
        }
        return total;
    }

    public double getDiscount(){
        return getSubtotal() - getSubtotalWithVoucher();
    }

    public boolean checkVoucher(String code){
        if(voucherService.getVoucherByCode(code).isEmpty())
            return false;
        Voucher voucher = voucherService.getVoucherByCode(code).get();
        return  voucher.getIsActive() && voucher.getEndDate()!=null && (voucher.getEndDate().isAfter(LocalDate.now()) || voucher.getEndDate().equals(LocalDate.now()));
    }

    public void saveVoucher(String code){
        cart.voucherCode = code;
    }

    public long getQuantity(){
        return cart.cartItems.stream().toList().size();
    }


}
