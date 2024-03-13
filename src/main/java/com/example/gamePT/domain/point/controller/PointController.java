package com.example.gamePT.domain.point.controller;

import com.example.gamePT.domain.point.entity.Point;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {

    private final UserService userService;

    @GetMapping("/order")
    public String getPointOrder(Point point) {

        return "point/point_order";
    }

    @PostMapping("/order")
    public String postPointOrder(@Valid Point point, BindingResult bindingResult, Model model, Principal principal) {
        SiteUser siteUser = this.userService.findByUsername(principal.getName());

        model.addAttribute("customer", siteUser);
        model.addAttribute("point", point);

        return "tosspayments/checkout";
    }
}
