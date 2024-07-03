package com.team12.DASpring.services;


import com.team12.DASpring.entity.*;
import com.team12.DASpring.repository.IOrderDetailRepository;
import com.team12.DASpring.repository.IOrderRepository;
import com.team12.DASpring.repository.IuserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    @Autowired
    private CartService cartService;

    @Autowired
    private IuserRepository userRepository;

    @Autowired
    private VoucherService voucherService;

    @Transactional
    public Order createOrder(String customerName, String address, String message, String phoneNumber, List<CartItem> cartItems){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        Voucher voucher = new Voucher();
        Order order = new Order();
        if(voucherService.getVoucherByCode(cartService.getVoucherCode()).isPresent()){
            voucher = voucherService.getVoucherByCode(cartService.getVoucherCode()).get();
            order.setTotal(cartService.getSubtotalWithVoucher());
            order.setDiscountPrice(cartService.getDiscount());
        }
        else{
            order.setTotal(cartService.getSubtotal());
            if (voucherService.getVoucherByCode("DEFAULT").isPresent())
                voucher = voucherService.getVoucherByCode("DEFAULT").get();
        }
        order.setVoucher(voucher);
        order.setUser(user);
        order.setCustomerName(customerName);
        order.setAddress(address);
        order.setOrderDate(LocalDateTime.now());
        order.setPhoneNumber(phoneNumber);
        order.setMessage(message);
        order.setOrderStatus("Đã đặt hàng");
        order = orderRepository.save(order);

        for (CartItem item: cartItems){
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setElectronicDevice(item.gettElectronicDevice());
            detail.setQuantity(item.gettQuantity());
            detail.setPrice(item.gettElectronicDevice().getPrice());
            orderDetailRepository.save(detail);
        }
        cartService.clearCart();
        return order;
    }

    public Optional<Order> getOrderById(Long id){
        return orderRepository.findById(id);
    }

    public Order updateStatus(@NotNull Order order, String status){
        Order existingOrder = orderRepository.findById(order.getId()).orElseThrow(()->new IllegalStateException("Order with id: "+order.getId()+" does not exit."));
        existingOrder.setOrderStatus(status);
        return  orderRepository.save(existingOrder);
    }

    public List<OrderDetail> getListOrderDetail(Long id){
        return orderDetailRepository.findAll().stream().filter(p->p.getOrder().getId().equals(id)).toList();
    }




}
