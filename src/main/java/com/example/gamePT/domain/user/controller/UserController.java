package com.example.gamePT.domain.user.controller;

import com.example.gamePT.domain.user.request.SiteUserRequest;
import com.example.gamePT.domain.user.response.SiteUserResponse;
import com.example.gamePT.domain.user.service.UserService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final Gson gson;


    // 회원가입
    @GetMapping("/signup")
    public String signupPage(SiteUserRequest.Signup signup){

        return "/user/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid SiteUserRequest.Signup signup, BindingResult bindingResult, @RequestParam(value = "profileImg") MultipartFile profileImg) throws IOException {

        if(!userService.signUp(signup,bindingResult,profileImg)){
            return "/user/signup";
        }

        return "redirect:/";
    }

    @PostMapping("/isUnique")
    @ResponseBody
    public SiteUserResponse.IsUnique isUniqueAjax(@RequestBody @Valid SiteUserRequest.IsUniqueAjax data, BindingResult bindingResult){
        return this.userService.isUnique(data,bindingResult);
    }

    // 회원 가입 시
    // 로그인
    @GetMapping("/login")
    public String loginPage(){

        return "/user/login";
    }


}
