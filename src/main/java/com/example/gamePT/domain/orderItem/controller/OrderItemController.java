package com.example.gamePT.domain.orderItem.controller;

import com.example.gamePT.domain.cartItem.entity.CartItem;
import com.example.gamePT.domain.cartItem.service.CartItemService;
import com.example.gamePT.domain.orderItem.service.OrderItemService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orderItem")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Getter
    @Setter
    public static class OrderItemsRequest{
        private List<String> cartItem_ids;
        private Integer total_price;
    }

    @Getter
    public static class OrderItemsCreateResponse{

        private final String msg;
        private final Boolean isSuccess;

        public OrderItemsCreateResponse(Boolean isSuccess, String msg){
            this.msg = msg;
            this.isSuccess = isSuccess;
        }
    }

    // 장바구니 구매
    @PostMapping("/createByCart")
    @ResponseBody
    public OrderItemsCreateResponse createByCart(@RequestBody OrderItemsRequest req){

        List<String> cartItem_ids = req.getCartItem_ids();

        if(cartItem_ids.isEmpty()){
            return new OrderItemsCreateResponse(false, "주문 항목 없음");
        }

        return orderItemService.createByCart(cartItem_ids, req.getTotal_price());
    }

    @Getter
    @Setter
    public static class OrderItemRequest{
        private Long course_id;
    }


    // 단건 구매
    @PostMapping("/create")
    @ResponseBody
    public OrderItemsCreateResponse create(@RequestBody OrderItemRequest orderItemRequest){

        return orderItemService.createByCourseId(orderItemRequest.course_id);
    }
}
