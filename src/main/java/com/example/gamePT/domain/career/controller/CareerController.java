package com.example.gamePT.domain.career.controller;

import com.example.gamePT.domain.career.service.CareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/career")
@RequiredArgsConstructor
public class CareerController {
    private final CareerService careerService;


}
