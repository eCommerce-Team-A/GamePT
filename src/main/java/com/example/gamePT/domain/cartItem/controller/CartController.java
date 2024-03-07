package com.example.gamePT.domain.cartItem.controller;

import com.example.gamePT.domain.cartItem.entity.CartItem;
import com.example.gamePT.domain.cartItem.request.CartItemRequest;
import com.example.gamePT.domain.cartItem.service.CartItemService;
import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.course.service.CourseService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartItemService cartItemService;
    private final CourseService courseService;
    private final UserService userService;

    @GetMapping("/add/{id}")
    @ResponseBody
    public String addCart(@PathVariable(value = "id")Long id, Principal principal){
        SiteUser buyer = this.userService.findByUsername(principal.getName());
        Course course = this.courseService.findCourseById(id);

        CartItem cartItem = this.cartItemService.findCartItem(buyer,course);

        if(cartItem == null && course.getAuthor().getUsername().equals(buyer.getUsername())){
            return "myCourse";
        }

        if (cartItem == null){
            this.cartItemService.create(buyer,course);
            return "success";
        }

        return "exist";
    }

    @GetMapping("/list")
    public String cartList (Model model,Principal principal){
        SiteUser buyer = this.userService.findByUsername(principal.getName());
        List<CartItem> cartList = this.cartItemService.findByBuyerId(buyer.getId());

        model.addAttribute("cartList",cartList);

        return "cart/cart_list";
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public Boolean deleteById (@PathVariable(value = "id")Long id, Principal principal){

        SiteUser buyer = this.userService.findByUsername(principal.getName());

        return this.cartItemService.deleteCartItemBy(id,buyer);
    }



}
