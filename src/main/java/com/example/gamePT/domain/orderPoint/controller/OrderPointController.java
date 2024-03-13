package com.example.gamePT.domain.orderPoint.controller;

import com.example.gamePT.domain.orderPoint.entity.OrderPoint;
import com.example.gamePT.domain.orderPoint.servcie.OrderPointService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orderPoint")
public class OrderPointController {

    private final OrderPointService orderPointService;
    private final UserService userService;

    @PostMapping("/create")
    @ResponseBody
    public OrderPoint create(@RequestParam(value = "point") Integer point){

        SiteUser siteUser = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        return orderPointService.create(siteUser,point);

    }

}
