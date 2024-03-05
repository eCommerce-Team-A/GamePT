package com.example.gamePT.domain.cartItem.controller;

import com.example.gamePT.domain.cartItem.entity.CartItem;
import com.example.gamePT.domain.cartItem.request.CartItemRequest;
import com.example.gamePT.domain.cartItem.service.CartItemService;
import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.course.service.CourseService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartItemService cartItemService;
    private final CourseService courseService;
    private final UserService userService;

    @GetMapping("/add")
    @ResponseBody
    public String addCart(@RequestBody CartItemRequest.AddCartRequest addCartRequest){
        SiteUser buyer = this.userService.findByUserId(addCartRequest.getBuyerId());
        Course course = this.courseService.findCourseById(addCartRequest.getCourseId());
        CartItem cartItem = this.cartItemService.findCartItem(buyer,course);
        if (cartItem == null){
            return "exist";
        }
        return "success";
    }
}
