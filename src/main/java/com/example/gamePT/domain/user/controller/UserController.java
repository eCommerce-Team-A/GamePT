package com.example.gamePT.domain.user.controller;

import com.example.gamePT.domain.user.request.SiteUserRequest;
import com.example.gamePT.domain.user.response.SiteUserResponse;
import com.example.gamePT.domain.user.service.UserService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // mypage
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage")
    public String mypage(){

        return "/user/mypage";
    }

    // Id/PW 찾기
    @GetMapping("/findUserInfo")
    public String findUserInfo(){

        return "/user/findUserInfo";
    }

    @PostMapping("/findUserInfo")
    @ResponseBody
    public SiteUserResponse.AjaxRes findUserInfo(@RequestBody SiteUserRequest.FindUserInfoAjax data){
        return this.userService.findUserInfoAjax(data);
    }

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
    public SiteUserResponse.AjaxRes isUniqueAjax(@RequestBody @Valid SiteUserRequest.IsUniqueAjax data, BindingResult bindingResult){
        return this.userService.isUnique(data,bindingResult);
    }

    // 회원 가입 시
    // 로그인
    @GetMapping("/login")
    public String loginPage(){

        return "/user/login";
    }


}
