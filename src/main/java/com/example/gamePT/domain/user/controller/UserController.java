package com.example.gamePT.domain.user.controller;

import com.example.gamePT.domain.careerCategory.entity.Category;
import com.example.gamePT.domain.careerCategory.service.CategoryService;
import com.example.gamePT.domain.duoArticle.enity.DuoArticle;
import com.example.gamePT.domain.expertRequest.entity.ExpertRequest;
import com.example.gamePT.domain.expertRequest.service.ExpertRequestService;
import com.example.gamePT.domain.order.entity.OrderEntity;
import com.example.gamePT.domain.order.service.OrderService;
import com.example.gamePT.domain.orderPoint.entity.OrderPoint;
import com.example.gamePT.domain.orderPoint.servcie.OrderPointService;
import com.example.gamePT.domain.qna.entity.QnA;
import com.example.gamePT.domain.qna.service.QnAService;
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
    private final QnAService qnAService;
    private final OrderPointService orderPointService;
    private final Gson gson;
    private final ExpertRequestService expertRequestService;
    private final CategoryService categoryService;

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage/orderPoint/{pageNumber}")
    public String orderPointReload(@PathVariable(value = "pageNumber") int pageNumber, Model model){

        SiteUser su = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Page<OrderPoint> op = this.orderPointService.getListForMyPage(pageNumber, su);

        model.addAttribute("orderPointList", op);

        return "user/mypage::#orderPointHistory";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage/qna/{pageNumber}")
    public String qnaReload(@PathVariable(value = "pageNumber") int pageNumber, Model model){

        SiteUser su = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Page<QnA> qnaList = this.qnAService.getListForMyPage(pageNumber, su);

        model.addAttribute("qnaList", qnaList);

        return "user/mypage::#qnaHistory";
    }

    // mypage
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage/order/{pageNumber}")
    public String orderReload(@PathVariable(value = "pageNumber") int pageNumber, Model model){

        SiteUser su = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Page<OrderEntity> paging = this.orderService.getList(pageNumber, su.getId());

        model.addAttribute("orderList", paging);

        return "user/mypage::#orderHistory";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage")
    public String mypage(Model model){

        SiteUser su = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Page<OrderEntity> paging = this.orderService.getList(0, su.getId());
        Page<QnA> qnaList = this.qnAService.getListForMyPage(0, su);
        Page<OrderPoint> orderPointList = this.orderPointService.getListForMyPage(0,su);

        model.addAttribute("orderList", paging);
        model.addAttribute("qnaList", qnaList);
        model.addAttribute("orderPointList", orderPointList);

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

    @GetMapping("/admin")
    public String adminPage(Model model) {
        SiteUser su = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Page<OrderEntity> paging = this.orderService.getList(0, su.getId());
        Page<QnA> qnaList = this.qnAService.getQnAList(0);
        List<ExpertRequest> expertRequestList = this.expertRequestService.getExpertRequestList();
        List<Category> categoryList = this.categoryService.getCategoryList();


        model.addAttribute("orderList", paging);
        model.addAttribute("qnaList", qnaList);
        model.addAttribute("expertRequestList", expertRequestList);
        model.addAttribute("categoryList", categoryList);
        return "admin/main";
    }
}
