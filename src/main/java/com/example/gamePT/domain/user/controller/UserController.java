package com.example.gamePT.domain.user.controller;

import com.example.gamePT.domain.duoArticle.enity.DuoArticle;
import com.example.gamePT.domain.order.entity.OrderEntity;
import com.example.gamePT.domain.order.service.OrderService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.request.SiteUserRequest;
import com.example.gamePT.domain.user.response.SiteUserResponse;
import com.example.gamePT.domain.user.service.UserService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final Gson gson;

    // update
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update")
    public String update(SiteUserRequest.Update updateForm, BindingResult bindingResult, @RequestParam(value = "profileImg") MultipartFile profileImg) throws IOException {

        if(!userService.update(updateForm,bindingResult,profileImg)){
            return "/user/update";
        }

        return "redirect:/user/mypage";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/update")
    public String update(SiteUserRequest.Update updateForm){

        return "/user/update";
    }

    // mypage
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage/{pageNumber}")
    public String mypageReload(@PathVariable(value = "pageNumber") int pageNumber, Model model){

        SiteUser su = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Page<OrderEntity> paging = this.orderService.getList(pageNumber, su.getId());

        model.addAttribute("paging", paging);

        return "user/mypage::#orderHistory";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage")
    public String mypage(Model model){

        SiteUser su = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Page<OrderEntity> paging = this.orderService.getList(0, su.getId());

        model.addAttribute("paging", paging);

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
