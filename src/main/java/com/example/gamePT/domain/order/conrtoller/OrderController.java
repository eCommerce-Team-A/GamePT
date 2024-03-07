package com.example.gamePT.domain.order.conrtoller;

import com.example.gamePT.domain.order.entity.OrderEntity;
import com.example.gamePT.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/detail/{orderId}")
    public String detail(@PathVariable(value = "orderId") Long orderId, Model model){

        OrderEntity oe = orderService.findById(orderId);

        model.addAttribute("order",oe);

        return "order/detail";
    }

}
