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

    @PostMapping("/signup")
    public String signup(@RequestBody @Valid SiteUserRequest.Signup signup, BindingResult bindingResult){

        if(!userService.signUp(signup,bindingResult)){
            return "/user/signup";
        }

        return "redirect:/";
    }

    @GetMapping("/signup")
    public String s1(@Valid SiteUserRequest.Signup signup, BindingResult bindingResult){

        return "redirect:/";
    }

}
