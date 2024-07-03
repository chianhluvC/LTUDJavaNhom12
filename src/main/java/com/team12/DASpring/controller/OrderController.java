package com.team12.DASpring.controller;


import com.team12.DASpring.entity.CartItem;
import com.team12.DASpring.entity.Order;
import com.team12.DASpring.repository.IuserRepository;
import com.team12.DASpring.services.CartService;
import com.team12.DASpring.services.OrderService;
import com.team12.DASpring.services.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping({"/order",""})
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private VNPAYService vnpayService;
    @Autowired
    private IuserRepository userRepository;

    @GetMapping("/checkout")
    public String checkout(Model model){
        model.addAttribute("cartItems", cartService.gettCartItems());
        model.addAttribute("subTotal", cartService.getSubtotal());
        if(cartService.checkVoucher(cartService.getVoucherCode()))
        {
            model.addAttribute("discount",cartService.getDiscount());
            model.addAttribute("total", cartService.getSubtotalWithVoucher());
        }
        else {
            model.addAttribute("total", cartService.getSubtotal());
        }

        return "/cart/checkout";
    }
    @PostMapping("/submit")
    public String submitOrder(String customerName, String phoneNumber, String address, String message, String paymentmethod, HttpServletRequest request, Model model){
        List<CartItem> cartItems = cartService.gettCartItems();
        if(cartItems.isEmpty()){
            return "redirect:cart";
        }
        Order order = orderService.createOrder(customerName,address,message,phoneNumber, cartItems);
        if(paymentmethod.equals("VNPay")){
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String VNPayUrl = vnpayService.createOrder(request,(int)order.getTotal(), String.valueOf(order.getId()), baseUrl);
            return "redirect:"+VNPayUrl;
        }
        model.addAttribute("order",order);
        model.addAttribute("details",orderService.getListOrderDetail(order.getId()));
        return "cart/order-confirmation";
    }


    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model){
        int paymentStatus = vnpayService.orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String amount = request.getParameter("vnp_Amount");
        Double totalPrice = Double.parseDouble(amount)/100;
        Order order = orderService.getOrderById(Long.parseLong(orderInfo)).orElseThrow(()->new IllegalArgumentException("Order with id: "+Long.parseLong(orderInfo)+" does not exit."));
        if(paymentStatus != 1){
            orderService.updateStatus(order,"Thanh toán VNPAY thất bại!");
            return "/payment/fail";
        }

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);
        orderService.updateStatus(order,"Thanh toán VNPAY thành công với mã giao dịch là: "+ transactionId);

        return "/payment/success";
    }


}
