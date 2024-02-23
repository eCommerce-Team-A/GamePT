package com.example.gamePT.domain.user.controller;

import com.example.gamePT.domain.user.request.SiteUserRequest;
import com.example.gamePT.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    // 회원가입
    @GetMapping("/signup")
    public String signupPage(SiteUserRequest.Signup signup){

        return "/user/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid SiteUserRequest.Signup signup, BindingResult bindingResult){

        if(!userService.signUp(signup,bindingResult)){
            return "/user/signup";
        }

        return "redirect:/";
    }

    // 회원 가입 시

    // 로그인
    @GetMapping("/login")
    public String loginPage(){

        return "/user/login";
    }


}
